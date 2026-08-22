package com.ecom.product_service.service.impl;

import com.ecom.product_service.dto.ProductImportDto;
import com.ecom.product_service.repository.ProductRepository;
import com.ecom.product_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductImportServiceImpl
        implements ProductImportService {

    private final ProductRepository productRepository;

    @Override
    public void validateProducts(
            List<ProductImportDto> products,
            List<String> categoryNames) {

        Set<String> availableCategories =
                new HashSet<>();

        for (String categoryName : categoryNames) {

            availableCategories.add(
                    categoryName.trim().toLowerCase()
            );
        }

        Set<String> productsInFile =
                new HashSet<>();

        for (ProductImportDto product : products) {

            // Check category
            String categoryName =
                    product.getCategoryName()
                            .trim();

            if (!availableCategories.contains(
                    categoryName.toLowerCase())) {

                throw new RuntimeException(
                        "Category not found in import file: "
                                + categoryName
                );
            }
            // Check duplicate product
            String productName =
                    product.getName().trim();

            if (!productsInFile.add(
                    productName.toLowerCase())) {

                throw new RuntimeException(
                        "Duplicate product in JSON file: "
                                + productName
                );
            }


            // -----------------------------
            // Check database
            // -----------------------------

            if (productRepository
                    .existsByNameIgnoreCase(productName)) {

                throw new RuntimeException(
                        "Product already exists: "
                                + productName
                );
            }
        }
    }
}
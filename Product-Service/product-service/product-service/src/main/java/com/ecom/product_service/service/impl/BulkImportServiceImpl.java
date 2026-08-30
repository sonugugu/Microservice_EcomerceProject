package com.ecom.product_service.service.impl;

import com.ecom.product_service.dto.*;
import com.ecom.product_service.entity.Category;
import com.ecom.product_service.entity.Product;
import com.ecom.product_service.repository.CategoryRepository;
import com.ecom.product_service.repository.ProductRepository;
import com.ecom.product_service.service.BulkImportService;
import com.ecom.product_service.service.CategoryImportService;
import com.ecom.product_service.service.ProductImportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BulkImportServiceImpl
        implements BulkImportService {

    private final ObjectMapper objectMapper;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final CategoryImportService categoryImportService;

    private final ProductImportService productImportService;


    @Override
    @Transactional
    public BulkImportResponse importData(
            MultipartFile file) {
        try {
            // 1. Read JSON file
            BulkImportRequest request =
                    objectMapper.readValue(
                            file.getInputStream(),
                            BulkImportRequest.class
                    );
            // 2. Validate JSON structure
            if (request == null) {
                throw new RuntimeException(
                        "JSON file is empty"
                );
            }
            if (request.getCategories() == null) {
                throw new RuntimeException(
                        "categories section is missing"
                );
            }
            if (request.getProducts() == null) {

                throw new RuntimeException(
                        "products section is missing"
                );
            }
            List<CategoryImportDto> categoryDtos =
                    request.getCategories();

            List<ProductImportDto> productDtos =
                    request.getProducts();

            // 3. Validate categories
            List<String> categoryNames =
                    categoryImportService
                            .validateCategories(categoryDtos);
            // 4. Validate products
            productImportService.validateProducts(
                    productDtos,
                    categoryNames
            );
            // 5. Create Categories
            List<Category> categories =
                    categoryDtos.stream()
                            .map(dto -> {
                                Category category =
                                        new Category();
                                category.setName(
                                        dto.getName().trim()
                                );
                                category.setDescription(
                                        dto.getDescription()
                                );
                                return category;
                            })
                            .toList();

            List<Category> savedCategories =
                    categoryRepository.saveAll(
                            categories
                    );
            // 6. Create Category Map
            Map<String, Category> categoryMap =
                    new HashMap<>();
            for (Category category : savedCategories) {
                categoryMap.put(
                        category.getName().toLowerCase(),
                        category
                );
            }
            // 7. Create Products
            List<Product> products =
                    productDtos.stream()
                            .map(dto -> {

                                Product product =
                                        new Product();

                                product.setName(
                                        dto.getName().trim()
                                );

                                product.setDescription(
                                        dto.getDescription()
                                );

                                product.setPrice(
                                        dto.getPrice()
                                );

                                product.setStockQuantity(
                                        dto.getStockQuantity()
                                );


                                Category category =
                                        categoryMap.get(
                                                dto.getCategoryName()
                                                        .trim()
                                                        .toLowerCase()
                                        );

                                if (category == null) {

                                    throw new RuntimeException(
                                            "Category not found: "
                                                    + dto.getCategoryName()
                                    );
                                }

                                product.setCategory(
                                        category
                                );

                                return product;

                            })
                            .toList();

            // 8. Save Products
            productRepository.saveAll(products);
            // 9. Return response
            return new BulkImportResponse(

                    savedCategories.size(),

                    products.size(),

                    "Bulk import completed successfully"
            );


        } catch (IOException e) {

            throw new RuntimeException(
                    "Invalid JSON file",
                    e
            );
        }
    }
}
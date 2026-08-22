package com.ecom.product_service.service.impl;

import com.ecom.product_service.dto.CategoryImportDto;
import com.ecom.product_service.entity.Category;
import com.ecom.product_service.repository.CategoryRepository;
import com.ecom.product_service.service.CategoryImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryImportServiceImpl implements CategoryImportService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<String> validateCategories(
            List<CategoryImportDto> categories) {

        Set<String> namesInFile = new HashSet<>();

        for (CategoryImportDto dto : categories) {

            String name = dto.getName().trim();

            // Duplicate inside JSON file
            if (!namesInFile.add(name.toLowerCase())) {

                throw new RuntimeException(
                        "Duplicate category in JSON file: " + name
                );
            }

            // Already exists in database
            if (categoryRepository.existsByNameIgnoreCase(name)) {

                throw new RuntimeException(
                        "Category already exists: " + name
                );
            }
        }

        return categories
                .stream()
                .map(dto -> dto.getName().trim())
                .toList();
    }
}
package com.ecom.product_service.service;

import com.ecom.product_service.dto.CategoryImportDto;

import java.util.List;

public interface CategoryImportService {

    List<String> validateCategories(List<CategoryImportDto> categories);
}
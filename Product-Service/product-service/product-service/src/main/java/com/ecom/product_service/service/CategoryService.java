package com.ecom.product_service.service;

import com.ecom.product_service.dto.CategoryRequestDto;
import com.ecom.product_service.dto.CategoryResponseDto;
import com.ecom.product_service.dto.ExtendedCategoryResponseDto;

import java.util.List;


public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    ExtendedCategoryResponseDto getCategoryById(String categoryId);
    List<ExtendedCategoryResponseDto> getAllCategories();
    CategoryResponseDto updateCategory(String categoryId,CategoryRequestDto categoryRequestDto);
    String deleteCategory(String categoryId);


}

package com.ecom.product_service.Mapper;

import com.ecom.product_service.dto.CategoryResponseDto;
import com.ecom.product_service.entity.Category;

public class CategoryMapping {

    public static CategoryResponseDto toCategoryResponseDto(Category category) {

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setCategoryId(category.getCategoryId());
        responseDto.setName(category.getName());
        responseDto.setDescription(category.getDescription());

        return responseDto;
    }

    public static Category toCategoryEntity(CategoryResponseDto categoryResponseDto) {

        Category category = new Category();
        category.setCategoryId(categoryResponseDto.getCategoryId());
        category.setName(categoryResponseDto.getName());
        category.setDescription(categoryResponseDto.getDescription());

        return category;
    }
}

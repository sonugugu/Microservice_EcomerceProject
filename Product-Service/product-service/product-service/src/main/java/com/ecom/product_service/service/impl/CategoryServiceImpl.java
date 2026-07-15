package com.ecom.product_service.service.impl;


import com.ecom.product_service.Mapper.CategoryMapping;
import com.ecom.product_service.Mapper.ProductMapping;
import com.ecom.product_service.dto.CategoryRequestDto;
import com.ecom.product_service.dto.CategoryResponseDto;
import com.ecom.product_service.dto.ExtendedCategoryResponseDto;
import com.ecom.product_service.entity.Category;
import com.ecom.product_service.entity.Product;
import com.ecom.product_service.exception.CategoryAlreadyExistsException;
import com.ecom.product_service.exception.CategoryNotFoundException;
import com.ecom.product_service.repository.CategoryRepository;
import com.ecom.product_service.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        categoryRepository.findByName(categoryRequestDto.getName())
                .ifPresent(category -> {
                    throw new CategoryAlreadyExistsException(
                            "Category already exists with name : "
                                    + categoryRequestDto.getName());
                });

        Category category= new Category();
        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());
        Category saveCategory=categoryRepository.save(category);
        return CategoryMapping.toCategoryResponseDto(saveCategory);
    }

    @Override
    public ExtendedCategoryResponseDto getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id : " + categoryId));
        return convertToExtendedCategoryResponseDto(category);
    }

    @Override
    public List<ExtendedCategoryResponseDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<ExtendedCategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        for (Category category : categoryList) {
            ExtendedCategoryResponseDto categoryResponseDto = convertToExtendedCategoryResponseDto(category);
            categoryResponseDtos.add(categoryResponseDto);
        }
        return categoryResponseDtos;

    }

    @Override
    public CategoryResponseDto updateCategory(String categoryId, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id : " + categoryId));
        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapping.toCategoryResponseDto(updatedCategory);
    }

    @Override
    public String deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id : " + categoryId));
        categoryRepository.delete(category);
        return  "Category " + categoryId +" deleted successfully";

    }
    private ExtendedCategoryResponseDto convertToExtendedCategoryResponseDto(Category category) {
        List<Product> productList = category.getProducts();
        return new ExtendedCategoryResponseDto(category.getCategoryId(), category.getName(), category.getDescription(),
                productList.stream().map(ProductMapping::toProductResponseDto).toList());

    }

}

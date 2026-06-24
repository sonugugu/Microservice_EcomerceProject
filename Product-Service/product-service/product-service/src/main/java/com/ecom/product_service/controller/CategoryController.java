package com.ecom.product_service.controller;


import com.ecom.product_service.dto.CategoryRequestDto;
import com.ecom.product_service.dto.CategoryResponseDto;
import com.ecom.product_service.dto.ExtendedCategoryResponseDto;
import com.ecom.product_service.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }
    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto categoryRequestDto)
    {
        return categoryService.createCategory(categoryRequestDto);
    }

    @GetMapping
    public List<ExtendedCategoryResponseDto> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public ExtendedCategoryResponseDto getCategoryById(@PathVariable String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/{categoryId}/update")
    public CategoryResponseDto updateCategory(@PathVariable String categoryId, @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.updateCategory(categoryId, categoryRequestDto);
    }
    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable String categoryId) {
       return categoryService.deleteCategory(categoryId);
    }

}

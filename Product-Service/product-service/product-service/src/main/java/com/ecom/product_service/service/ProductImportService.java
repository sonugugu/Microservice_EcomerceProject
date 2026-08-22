package com.ecom.product_service.service;

import com.ecom.product_service.dto.ProductImportDto;

import java.util.List;

public interface ProductImportService {

    void validateProducts(
            List<ProductImportDto> products,
            List<String> categoryNames
    );
}
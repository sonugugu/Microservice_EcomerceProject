package com.ecom.product_service.Mapper;

import com.ecom.product_service.dto.ProductResponseDto;
import com.ecom.product_service.entity.Product;

public class ProductMapping {

    public static ProductResponseDto toProductResponseDto(Product product) {

        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setProductId(product.getProductId());
        responseDto.setName(product.getName());
        responseDto.setDescription(product.getDescription());
        responseDto.setPrice(product.getPrice());
        responseDto.setStockQuantity(product.getStockQuantity());
        responseDto.setInStock(product.getInStock());
        responseDto.setCategoryName(product.getCategory().getName());

        return responseDto;
    }

    public static Product toProductEntity(ProductResponseDto productResponseDto) {

        Product product = new Product();
        product.setProductId(productResponseDto.getProductId());
        product.setName(productResponseDto.getName());
        product.setDescription(productResponseDto.getDescription());
        product.setPrice(productResponseDto.getPrice());
        product.setStockQuantity(productResponseDto.getStockQuantity());
        product.setInStock(productResponseDto.getInStock());

        return product;
    }
}

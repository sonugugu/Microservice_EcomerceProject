package com.ecom.product_service.dto;


import lombok.Data;

@Data
public class ProductRequestDto {

    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String categoryId;

}

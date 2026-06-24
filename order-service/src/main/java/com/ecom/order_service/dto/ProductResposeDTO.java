package com.ecom.order_service.dto;

import lombok.Data;

@Data
public class ProductResposeDTO {
    private  String productId;
    private String name;
    private Double price;
    private int stockQuantity;
}

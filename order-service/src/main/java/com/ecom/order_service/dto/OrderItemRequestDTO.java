package com.ecom.order_service.dto;

import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private String productId;
    private Integer quantity;
}

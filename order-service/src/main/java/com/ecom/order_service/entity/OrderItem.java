package com.ecom.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    private String orderItemId;
    private String orderId;
    private String productId;
    private Double price;
    private Integer quantity;

}

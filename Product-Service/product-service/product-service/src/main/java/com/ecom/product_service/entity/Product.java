package com.ecom.product_service.entity;

import com.ecom.product_service.config.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String productId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean inStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    @PreUpdate
    public void updateStockStatus() {

        this.inStock = this.stockQuantity != null && this.stockQuantity > 0;

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();

        if (this.productId==null){
          this.productId="prod="+String.format("%05d", IdGenerator.getNextProductId());
        }

    }
}

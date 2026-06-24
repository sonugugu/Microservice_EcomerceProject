package com.ecom.product_service.entity;


import com.ecom.product_service.config.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String  categoryId;
    private String name;
    private String description;
    @OneToMany (mappedBy="category",cascade = CascadeType.ALL)
    private List<Product> products;

    @PrePersist
    public void generateId(){
        if(this.categoryId==null)
        {
            this.categoryId="cat-"+String.format("%05d", IdGenerator.getNextCategoryId());
        }
    }

}

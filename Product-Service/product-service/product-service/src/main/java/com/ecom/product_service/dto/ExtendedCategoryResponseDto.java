package com.ecom.product_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;
@Data
@NoArgsConstructor
public class ExtendedCategoryResponseDto extends CategoryResponseDto{
    List<ProductResponseDto> products;

    public ExtendedCategoryResponseDto(String categoryId,String name,String description,List<ProductResponseDto> products)
    {
        super(categoryId,name,description);
        this.products=products;
    }
}

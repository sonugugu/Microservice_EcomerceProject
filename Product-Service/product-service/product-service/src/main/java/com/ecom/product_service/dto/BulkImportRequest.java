package com.ecom.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkImportRequest {

    private List<CategoryImportDto> categories;

    private List<ProductImportDto> products;
}
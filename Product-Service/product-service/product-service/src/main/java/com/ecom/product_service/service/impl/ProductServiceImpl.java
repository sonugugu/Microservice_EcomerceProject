package com.ecom.product_service.service.impl;

import com.ecom.product_service.dto.ProductRequestDto;
import com.ecom.product_service.dto.ProductResponseDto;
import com.ecom.product_service.entity.Category;
import com.ecom.product_service.entity.Product;
import com.ecom.product_service.exception.CategoryNotFoundException;
import com.ecom.product_service.exception.ProductAlreadyExistsException;
import com.ecom.product_service.exception.ProductNotFoundException;
import com.ecom.product_service.repository.CategoryRepository;
import com.ecom.product_service.repository.ProductRepository;
import com.ecom.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        productRepository.findByName(productRequestDto.getName())
                .ifPresent(product -> {
                    throw new ProductAlreadyExistsException(
                            "Product already exists with name : "
                                    + productRequestDto.getName());
                });

        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id : "
                                        + productRequestDto.getCategoryId()));

        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductResponseDto getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return convertToDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ProductResponseDto updateStock(String productId, Integer stockQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException( "Product not found with id : " + productId));
       // product.setStockQuantity(stockQuantity);
        // if you want to add stock quantity each time, then use below code
        product.setStockQuantity(product.getStockQuantity() + stockQuantity);
        productRepository.save(product);
        return convertToDto(product);

    }

    @Override
    public String deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException( "Product not found with id : " + productId));
        productRepository.delete(product);
        return "Product " + productId +" deleted successfully";
    }

    private ProductResponseDto convertToDto(Product product) {

        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setProductId(product.getProductId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStockQuantity(product.getStockQuantity());
        productResponseDto.setInStock(product.getInStock());
        productResponseDto.setCategoryName(product.getCategory().getName());
        return productResponseDto;

    }


}

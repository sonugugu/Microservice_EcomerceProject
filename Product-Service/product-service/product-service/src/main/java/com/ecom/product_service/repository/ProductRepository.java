package com.ecom.product_service.repository;

import com.ecom.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);


}

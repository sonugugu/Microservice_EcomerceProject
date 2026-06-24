package com.Apigateway.Apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/product")
    public ResponseEntity<String> fallbackProduct() {
        return ResponseEntity.ok("Product service is currently unavailable. Please try again later.");
    }
}

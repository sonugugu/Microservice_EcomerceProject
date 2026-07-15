package com.ecom.product_service.advice;
import com.ecom.product_service.dto.ExceptionResponseDto;
import com.ecom.product_service.exception.CategoryAlreadyExistsException;
import com.ecom.product_service.exception.CategoryNotFoundException;
import com.ecom.product_service.exception.ProductAlreadyExistsException;
import com.ecom.product_service.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleCategoryNotFoundException(
            CategoryNotFoundException ex,
            WebRequest request) {

        ExceptionResponseDto response = new ExceptionResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleProductNotFoundException(
            ProductNotFoundException ex,
            WebRequest request) {

        ExceptionResponseDto response = new ExceptionResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDto> handleCategoryAlreadyExistsException(
            CategoryAlreadyExistsException ex,
            WebRequest request) {

        ExceptionResponseDto response = new ExceptionResponseDto(
                request.getDescription(false),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDto> handleProductAlreadyExistsException(
            ProductAlreadyExistsException ex,
            WebRequest request) {

        ExceptionResponseDto response = new ExceptionResponseDto(
                request.getDescription(false),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGlobalException(
            Exception ex,
            WebRequest request) {

        ExceptionResponseDto response = new ExceptionResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
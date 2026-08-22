package com.ecom.product_service.advice;
import com.ecom.product_service.dto.ExceptionResponseDto;
import com.ecom.product_service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
    // Adding for Bulk import
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField()
                                + ": "
                                + error.getDefaultMessage()
                )
                .collect(Collectors.joining(", "));

        ExceptionResponseDto response =
                new ExceptionResponseDto(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        LocalDateTime.now()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
    @ExceptionHandler(InvalidImportFileException.class)
    public ResponseEntity<ExceptionResponseDto>
    handleInvalidImportFile(
            InvalidImportFileException ex,
            HttpServletRequest request) {

        ExceptionResponseDto response =
                new ExceptionResponseDto(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
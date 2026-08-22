package com.ecom.product_service.exception;

public class InvalidImportFileException
        extends RuntimeException {

    public InvalidImportFileException(String message) {
        super(message);
    }

    public InvalidImportFileException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
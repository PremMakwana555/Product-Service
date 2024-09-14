package com.example.product_service.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

package com.example.product_service.exceptions;

public class RestTemplateException extends RuntimeException {
    public RestTemplateException(String message) {
        super(message);
    }

    public RestTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}

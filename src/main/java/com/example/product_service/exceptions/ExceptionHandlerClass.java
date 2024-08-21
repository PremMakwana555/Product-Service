package com.example.product_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.logging.Logger;

@RestControllerAdvice
public class ExceptionHandlerClass {

    Logger logger = Logger.getLogger(getClass().getName());

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<Object> handleException(Exception e) {
        logger.info("Exception occurred: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}

package com.example.product_service;

import org.springframework.stereotype.Component;

@Component
public class A {
    B b;
    A(B b) {
        this.b = b;
    }
}

package com.example.beanscheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {
    B b;
    A(B b) {
        System.out.println("A constructor");
        this.b = b;
    }
}

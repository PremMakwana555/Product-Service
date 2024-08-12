package com.example.beanscheck.controllers;

import com.example.beanscheck.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/product/{id}")
    public void getProductById(@PathVariable("id") Long id) {
        System.out.println("ProductController.getProductById");
    }

    @GetMapping("/products")
    public ProductResponseDto getAllProducts() {
        System.out.println("ProductController.getAllProducts");
        return ;
    }
}

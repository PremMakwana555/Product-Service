package com.example.beanscheck.controllers;

import com.example.beanscheck.dto.ProductResponseDto;
import com.example.beanscheck.mapper.ProductMapper;
import com.example.beanscheck.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        System.out.println("ProductController.getProductById");
        ProductResponseDto productDto = ProductMapper.INSTANCE.ProductDtoToProduct(productService.getProductById(id));
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        System.out.println("ProductController.getAllProducts");
        return ResponseEntity.ok(productService.getAllProducts().stream().map(ProductMapper.INSTANCE::ProductDtoToProduct).toList());
    }
}

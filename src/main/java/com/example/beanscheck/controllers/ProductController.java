package com.example.beanscheck.controllers;

import com.example.beanscheck.dto.ProductResponseDto;
import com.example.beanscheck.mapper.ProductMapper;
import com.example.beanscheck.models.Product;
import com.example.beanscheck.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/product")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductResponseDto productDto) {
        System.out.println("ProductController.addProduct");
        Product product = productService.addProduct(productDto);
        return ResponseEntity.ok(ProductMapper.INSTANCE.ProductDtoToProduct(product));
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductResponseDto productDto) {
        System.out.println("ProductController.updateProduct");
        Product product = productService.partiallyUpdate(id, productDto);
        return ResponseEntity.ok(ProductMapper.INSTANCE.ProductDtoToProduct(product));
    }
}

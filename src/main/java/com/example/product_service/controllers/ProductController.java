package com.example.product_service.controllers;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import com.example.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProductController {

    Logger logger = Logger.getLogger(getClass().getName());

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        logger.info("ProductController.getProductById");
        ProductDto productDto = ProductMapper.INSTANCE.productDtoToProduct(productService.getProductById(id));
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        logger.info("ProductController.getAllProducts");
        return ResponseEntity.ok(productService.getAllProducts().stream().map(ProductMapper.INSTANCE::productDtoToProduct).toList());
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        logger.info("ProductController.addProduct");
        Product product = productService.addProduct(productDto);
        return ResponseEntity.ok(ProductMapper.INSTANCE.productDtoToProduct(product));
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        logger.info("ProductController.updateProduct");
        Product product = productService.partiallyUpdate(id, productDto);
        return ResponseEntity.ok(ProductMapper.INSTANCE.productDtoToProduct(product));
    }
}

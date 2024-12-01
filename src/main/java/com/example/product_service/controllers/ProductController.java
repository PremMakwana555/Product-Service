package com.example.product_service.controllers;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import com.example.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class ProductController {

    Logger logger = Logger.getLogger(getClass().getName());

    private final ProductService productService;

    @Autowired
    public ProductController(@Qualifier("productServiceDb") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        logger.info("ProductController.getProductById");
        ProductDto productDto = ProductMapper.INSTANCE.productDtoToProduct(productService.getProductById(id));
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<PagedModel<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        logger.info("ProductController.getAllProducts");
        Pageable pageable = PageRequest.of(page, size);
        // Convert Page<Product> to Page<ProductDto>
        Page<ProductDto> productDtoPage = productService.getAllProducts(pageable)
                .map(ProductMapper.INSTANCE::productToProductDto);
        // Create PagedModel with content
        PagedModel<ProductDto> pagedModel = new PagedModel<>(productDtoPage);
        return ResponseEntity.ok(pagedModel);

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

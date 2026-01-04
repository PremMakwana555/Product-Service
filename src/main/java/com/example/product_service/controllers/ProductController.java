package com.example.product_service.controllers;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import com.example.product_service.services.CategoryService;
import com.example.product_service.services.ProductService;
import com.example.product_service.services.ProductSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProductController {

    Logger logger = Logger.getLogger(getClass().getName());

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductSyncService productSyncService;

    @Autowired
    public ProductController(@Qualifier("productServiceDb") ProductService productService,
            CategoryService categoryService, ProductSyncService productSyncService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productSyncService = productSyncService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        ProductDto productDto = ProductMapper.INSTANCE.productDtoToProduct(productService.getProductById(id));
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<PagedModel<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> productDtoPage = productService.getAllProducts(pageable)
                .map(ProductMapper.INSTANCE::productToProductDto);
        PagedModel<ProductDto> pagedModel = new PagedModel<>(productDtoPage);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductDto>> getProductById(@RequestParam String name) {
        logger.info("ProductController.getProductById");
        List<ProductDto> productDtoList = categoryService.getProductsByCategory(name)
                .stream().map(ProductMapper.INSTANCE::productToProductDto).toList();
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        logger.info("ProductController.addProduct");
        Product product = productService.addProduct(productDto);
        // Note: productSyncService is called inside productService.addProduct
        return ResponseEntity.ok(ProductMapper.INSTANCE.productDtoToProduct(product));
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        logger.info("ProductController.updateProduct");
        Product product = productService.partiallyUpdate(id, productDto);
        return ResponseEntity.ok(ProductMapper.INSTANCE.productDtoToProduct(product));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long id) {
        logger.info("ProductController.getProductById");
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully with id: " + id);
    }

}

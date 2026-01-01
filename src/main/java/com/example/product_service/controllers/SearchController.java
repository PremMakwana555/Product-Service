package com.example.product_service.controllers;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import com.example.product_service.services.ProductSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class SearchController {

    private final ProductSearchService productSearchService;
    @Autowired
    public SearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/products/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<Product> productPage = productSearchService.searchProducts(keyword, pageable);
        Page<ProductDto> productDtoPage = productPage.map(ProductMapper.INSTANCE::productToProductDto);

        return ResponseEntity.ok(productDtoPage);
    }
}

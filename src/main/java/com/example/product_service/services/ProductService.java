package com.example.product_service.services;

import com.example.product_service.dto.ProductResponseDto;
import com.example.product_service.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public Product getProductById(Long id) ;

    public List<Product> getAllProducts();

    public Product addProduct(ProductResponseDto productResponseDto);

    public Product partiallyUpdate(Long id, ProductResponseDto productDto);
}

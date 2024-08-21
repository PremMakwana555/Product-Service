package com.example.beanscheck.services;

import com.example.beanscheck.dto.ProductResponseDto;
import com.example.beanscheck.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public Product getProductById(Long id) ;

    public List<Product> getAllProducts();

    public Product addProduct(ProductResponseDto productResponseDto);

    public Product partiallyUpdate(Long id, ProductResponseDto productDto);
}

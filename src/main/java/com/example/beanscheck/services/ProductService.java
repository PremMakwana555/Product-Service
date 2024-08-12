package com.example.beanscheck.services;

import com.example.beanscheck.models.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public Product getProductById(Long id) ;

}

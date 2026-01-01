package com.example.product_service.services;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public Product getProductById(Long id) ;

    public Page<Product> getAllProducts(Pageable pageable);

    public Product addProduct(ProductDto productDto);

    public Product partiallyUpdate(Long id, ProductDto productDto);

    public void deleteProduct(Long id);
}

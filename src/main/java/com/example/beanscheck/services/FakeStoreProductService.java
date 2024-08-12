package com.example.beanscheck.services;

import com.example.beanscheck.dto.FakeStoreProductResponseDto;
import com.example.beanscheck.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(Long id) {
        restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreProductResponseDto.class, id);
    }
}

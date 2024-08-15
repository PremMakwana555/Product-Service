package com.example.beanscheck.services;

import com.example.beanscheck.dto.FakeStoreProductResponseDto;
import com.example.beanscheck.mapper.ProductMapper;
import com.example.beanscheck.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductResponseDto dto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreProductResponseDto.class, id);
        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(dto);
    }

    @Override
    public List<Product> getAllProducts() {
//        TODO: Using exchange method
//        ResponseEntity<List<FakeStoreProductResponseDto>> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<FakeStoreProductResponseDto>>() {}
//        );

        // TODO: Using the get for Entity method
        ResponseEntity<FakeStoreProductResponseDto[]> responseEntity = restTemplate.
                getForEntity("https://fakestoreapi.com/products",
                        FakeStoreProductResponseDto[].class);

        return Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
                .map(ProductMapper.INSTANCE::fakeStoreProductResponseDtoToProduct).toList();

    }


}

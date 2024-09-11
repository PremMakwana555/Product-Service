package com.example.product_service.services;

import com.example.product_service.dto.FakeStoreProductResponseDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Override
    public Product addProduct(ProductDto productDto) {

        FakeStoreProductResponseDto requestDto = ProductMapper.INSTANCE.productDtoToFakeStoreProductResponseDto(productDto);

        ResponseEntity<FakeStoreProductResponseDto> responseEntity =
                restTemplate.postForEntity("https://fakestoreapi.com/products", requestDto, FakeStoreProductResponseDto.class);

        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(Objects.requireNonNull(responseEntity.getBody()));
    }

    @Override
    public Product partiallyUpdate(Long id, ProductDto productDto) {
        FakeStoreProductResponseDto requestDto = ProductMapper.INSTANCE.productDtoToFakeStoreProductResponseDto(productDto);

        FakeStoreProductResponseDto responseDto = restTemplate.patchForObject(
                "http://fakestoreapi.com/products/{id}",
                requestDto,
                FakeStoreProductResponseDto.class, id
        );

        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(responseDto);

//        HttpEntity<FakeStoreProductResponseDto> requestEntity = new HttpEntity<>(requestDto);
//
//        ResponseEntity<FakeStoreProductResponseDto> responseEntity = restTemplate.exchange(
//                "http://fakestoreapi.com/products/" + id,
//                HttpMethod.PATCH,
//                requestEntity,
//                FakeStoreProductResponseDto.class
//        );
//        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(responseEntity.getBody());
    }


}

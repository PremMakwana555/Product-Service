package com.example.product_service.services;

import com.example.product_service.dto.FakeStoreProductResponseDto;
import com.example.product_service.dto.ProductDto;
import com.example.product_service.exceptions.RestTemplateException;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(FakeStoreProductService.class);
    @Value("${rest-template.base-url}")
    private String BASE_URL;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductResponseDto dto = restTemplate.getForObject(BASE_URL + "/{id}", FakeStoreProductResponseDto.class, id);
        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(dto);

    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
//        TODO: Using exchange method
//        ResponseEntity<List<FakeStoreProductResponseDto>> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<FakeStoreProductResponseDto>>() {}
//        );

        // TODO: Using the get for Entity method
//        ResponseEntity<FakeStoreProductResponseDto[]> responseEntity = restTemplate.
//                getForEntity("https://fakestoreapi.com/products",
//                        FakeStoreProductResponseDto[].class);
//
//        return Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
//                .map(ProductMapper.INSTANCE::fakeStoreProductResponseDtoToProduct).toList();

        ResponseEntity<FakeStoreProductResponseDto[]> responseEntity =
                restTemplate.getForEntity(BASE_URL, FakeStoreProductResponseDto[].class);

        List<Product> products = Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
                .map(ProductMapper.INSTANCE::fakeStoreProductResponseDtoToProduct)
                .toList();

        // Apply pagination logic manually
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        List<Product> paginatedProducts = products.subList(start, end);

        return new PageImpl<>(paginatedProducts, pageable, products.size());

    }

    @Override
    public Product addProduct(ProductDto productDto) {

        FakeStoreProductResponseDto requestDto = ProductMapper.INSTANCE.productDtoToFakeStoreProductResponseDto(productDto);

        try {
            ResponseEntity<FakeStoreProductResponseDto> responseEntity =
                    restTemplate.postForEntity(BASE_URL, requestDto, FakeStoreProductResponseDto.class);

            if (responseEntity.getBody() == null) {
                logger.error("Failed to add product: response body is null");
                throw new RestTemplateException("Failed to add product: response body is null");
            }

            return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(responseEntity.getBody());
        } catch (Exception e) {
            logger.error("Error occurred while adding product: {}", e.getMessage());
            throw new RestTemplateException("Error occurred while adding product", e);
        }
    }

    @Override
    public Product partiallyUpdate(Long id, ProductDto productDto) {
        FakeStoreProductResponseDto requestDto = ProductMapper.INSTANCE.productDtoToFakeStoreProductResponseDto(productDto);

        FakeStoreProductResponseDto responseDto = restTemplate.patchForObject(
                BASE_URL + "/{id}",
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

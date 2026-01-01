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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(FakeStoreProductService.class);
    @Value("${rest-template.base-url}")
    private String BASEURL;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductResponseDto dto = restTemplate.getForObject(BASEURL + "/{id}", FakeStoreProductResponseDto.class, id);
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
        ResponseEntity<FakeStoreProductResponseDto[]> responseEntity;

        try {
            responseEntity = restTemplate.getForEntity(BASEURL, FakeStoreProductResponseDto[].class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle errors such as 4xx or 5xx status codes
            throw new RestTemplateException("Error fetching products from FakeStore API", e);
        }

        // Check if the response body is null or empty
        FakeStoreProductResponseDto[] body = responseEntity.getBody();
        if (body == null || body.length == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        // Map response body to Product list
        List<Product> products = Arrays.stream(body)
                .map(ProductMapper.INSTANCE::fakeStoreProductResponseDtoToProduct)
                .toList();

        // Apply pagination logic manually
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), products.size());
        List<Product> paginatedProducts = products.subList(start, end);


        return new PageImpl<>(paginatedProducts, pageable, products.size());

    }

    @Override
    public Product addProduct(ProductDto productDto) {

        FakeStoreProductResponseDto requestDto = ProductMapper.INSTANCE.productDtoToFakeStoreProductResponseDto(productDto);

        try {
            ResponseEntity<FakeStoreProductResponseDto> responseEntity =
                    restTemplate.postForEntity(BASEURL, requestDto, FakeStoreProductResponseDto.class);

            if (responseEntity.getBody() == null) {
                logger.error("Failed to add product: response body is null");
                throw new RestTemplateException("Failed to add product: response body is null");
            }

            return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(responseEntity.getBody());
        } catch (Exception e) {
            throw new RestTemplateException("Error occurred while adding product", e);
        }
    }

    @Override
    public Product partiallyUpdate(Long id, ProductDto productDto) {
        FakeStoreProductResponseDto requestDto = ProductMapper.INSTANCE.productDtoToFakeStoreProductResponseDto(productDto);

        FakeStoreProductResponseDto responseDto = restTemplate.patchForObject(
                BASEURL + "/{id}",
                requestDto,
                FakeStoreProductResponseDto.class, id
        );

        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(responseDto);

        /*
        HttpEntity<FakeStoreProductResponseDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<FakeStoreProductResponseDto> responseEntity = restTemplate.exchange(
                "http://fakestoreapi.com/products/" + id,
                HttpMethod.PATCH,
                requestEntity,
                FakeStoreProductResponseDto.class
        );
        return ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(responseEntity.getBody());
        */
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete(BASEURL + "/{id}", id);
    }
}

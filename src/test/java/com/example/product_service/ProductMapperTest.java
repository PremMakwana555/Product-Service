package com.example.product_service;

import com.example.product_service.dto.FakeStoreProductResponseDto;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {

    @Test
    void testMapping() {
        FakeStoreProductResponseDto dto = new FakeStoreProductResponseDto();
        dto.setId(1L);
        dto.setName("Test Product");
        dto.setDescription("Test Description");
        dto.setPrice(100.0);
        dto.setImage("test-image-url");
        dto.setCategory("Test Category");

        Product product = ProductMapper.INSTANCE.fakeStoreProductResponseDtoToProduct(dto);

        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getImage(), product.getImageUrl());
        assertEquals(dto.getCategory(), product.getCategory().getName());
    }
}
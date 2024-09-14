package com.example.product_service.dto;

import lombok.Data;

@Data
public class FakeStoreProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private String category;
}
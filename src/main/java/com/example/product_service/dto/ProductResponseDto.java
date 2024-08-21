package com.example.product_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryName;
}

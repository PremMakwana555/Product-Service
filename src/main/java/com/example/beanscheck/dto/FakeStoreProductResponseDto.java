package com.example.beanscheck.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FakeStoreProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private String category;
}
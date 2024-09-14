package com.example.product_service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "product")
@Table
@NoArgsConstructor
public class Product extends BaseEntity {
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    @ManyToOne
    private Category category;
}
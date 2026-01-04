package com.example.product_service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import java.math.BigDecimal;

@Data
@Entity(name = "product")
@Table
@NoArgsConstructor
@Document(indexName = "products")
public class Product extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
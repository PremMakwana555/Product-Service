package com.example.product_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table
@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
    private String name;
    private String description;
    @OneToMany
    private List<Product> products;

    public Category(String categoryName, String categoryDescription) {
        this.description = categoryDescription;
        this.name = categoryName;
    }
}

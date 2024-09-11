package com.example.product_service.services;

import com.example.product_service.models.Category;

import java.util.Optional;

public interface CategoryService {
    Category getCategoryByName(String categoryName, String categoryDescription);

}

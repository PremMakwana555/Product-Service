package com.example.product_service.services;

import com.example.product_service.models.Category;
import com.example.product_service.models.Product;

import java.util.List;

public interface CategoryService {
    Category getCategoryByName(String categoryName, String categoryDescription);

    List<Product> getProductsByCategory(String categoryName);
}

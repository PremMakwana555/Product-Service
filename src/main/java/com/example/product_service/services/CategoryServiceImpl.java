package com.example.product_service.services;

import com.example.product_service.exceptions.CategoryNotFoundException;
import com.example.product_service.models.Category;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.jpa.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category getCategoryByName(String categoryName, String categoryDescription) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByName(categoryName));
        if (category.isEmpty()) {
            Category newCategory = new Category(categoryName, categoryDescription);
            categoryRepository.save(newCategory);
            return newCategory;
        }
        return category.get();
    }

    public List<Product> getProductsByCategory(String categoryName) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByName(categoryName));
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category not found with name: " + categoryName);
        }
        return category.get().getProducts();
    }
}

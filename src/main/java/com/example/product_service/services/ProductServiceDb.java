package com.example.product_service.services;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.exceptions.ProductNotFoundException;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Category;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.ProductRepository;
import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Priority(1)
public class ProductServiceDb implements ProductService{

    private final ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;


    @Autowired
    public ProductServiceDb(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        Category category = categoryService.getCategoryByName(productDto.getCategoryName(), productDto.getCategoryDescription());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product partiallyUpdate(Long id, ProductDto productDto){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        return new Product();
    }
}

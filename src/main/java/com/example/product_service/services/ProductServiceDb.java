package com.example.product_service.services;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.exceptions.ProductNotFoundException;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Category;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.ProductRepository;
import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("productServiceDb")
@Priority(1)
public class ProductServiceDb implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;


    @Autowired
    public ProductServiceDb(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
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

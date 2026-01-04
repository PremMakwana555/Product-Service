package com.example.product_service.services;

import com.example.product_service.dto.ProductDto;
import com.example.product_service.exceptions.ProductNotFoundException;
import com.example.product_service.kafka.event.ProductCreatedEvent;
import com.example.product_service.kafka.producer.ProductEventProducer;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.models.Category;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.jpa.ProductRepository;
import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service("productServiceDb")
@Priority(1)
public class ProductServiceDb implements ProductService {

    private final ProductRepository productRepository;
    private final ProductSyncService productSyncService;
    private final CategoryService categoryService;
    private final ProductEventProducer productEventProducer;

    @Autowired
    public ProductServiceDb(ProductRepository productRepository, ProductSyncService productSyncService,
            CategoryService categoryService, ProductEventProducer productEventProducer) {
        this.productRepository = productRepository;
        this.productSyncService = productSyncService;
        this.categoryService = categoryService;
        this.productEventProducer = productEventProducer;
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
        Category category = categoryService.getCategoryByName(productDto.getCategoryName(),
                productDto.getCategoryDescription());
        product.setCategory(category);
        Product product1 = productRepository.save(product);
        productSyncService.saveProduct(product1);

        // Publish ProductCreatedEvent to Kafka
        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(product1.getId())
                .title(product1.getName())
                .description(product1.getDescription())
                .price(product1.getPrice())
                .categoryId(product1.getCategory().getId())
                .imageUrl(product1.getImageUrl())
                .build();
        productEventProducer.publishProductCreatedEvent(event);

        return product1;
    }

    @Override
    public Product partiallyUpdate(Long id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        return new Product();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

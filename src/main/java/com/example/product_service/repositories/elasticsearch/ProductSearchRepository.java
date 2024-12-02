package com.example.product_service.repositories.elasticsearch;

import com.example.product_service.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
    Page<Product> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}

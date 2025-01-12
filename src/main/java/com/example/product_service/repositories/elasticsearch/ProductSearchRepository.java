package com.example.product_service.repositories.elasticsearch;

import com.example.product_service.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {

    @Query("{\"bool\": {\"should\": ["
            + "{\"fuzzy\": {\"name\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}},"
            + "{\"fuzzy\": {\"description\": {\"value\": \"?1\", \"fuzziness\": \"AUTO\"}}}"
            + "]}}")
    Page<Product> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}

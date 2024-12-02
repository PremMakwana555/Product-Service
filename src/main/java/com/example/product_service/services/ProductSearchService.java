package com.example.product_service.services;
import com.example.product_service.models.Product;
import com.example.product_service.repositories.elasticsearch.ProductSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public ProductSearchService(ProductSearchRepository productSearchRepository) {
        this.productSearchRepository = productSearchRepository;
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productSearchRepository.findByNameContainingOrDescriptionContaining(keyword, keyword, pageable);
    }
}

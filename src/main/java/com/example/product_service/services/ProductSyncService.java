package com.example.product_service.services;

import com.example.product_service.models.Product;
import com.example.product_service.repositories.elasticsearch.ProductSearchRepository;
import com.example.product_service.repositories.jpa.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
public class ProductSyncService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Scheduled(fixedRate = 3600000)
    public void syncProducts() {
        log.info("Syncing products");
        List<Product> products = productRepository.findAll();
        productSearchRepository.saveAll(products);
    }

    public void saveProduct(Product product) {
        productSearchRepository.save(product);
    }
}
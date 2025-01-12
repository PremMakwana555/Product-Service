package com.example.product_service.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.product_service.repositories.jpa")  // JPA repositories
@EnableElasticsearchRepositories(basePackages = "com.example.product_service.repositories.elasticsearch")  // Elasticsearch repositories
public class RepositoryConfig {}

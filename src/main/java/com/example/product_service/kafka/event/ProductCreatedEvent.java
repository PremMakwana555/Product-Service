package com.example.product_service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent {
    private Long productId;
    private String title;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String imageUrl;

    // Event metadata
    private String correlationId;
    private String timestamp;
    private String source;
}

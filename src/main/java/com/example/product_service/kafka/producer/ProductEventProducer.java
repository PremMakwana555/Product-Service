package com.example.product_service.kafka.producer;

import com.example.product_service.kafka.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.product-events}")
    private String productEventsTopic;

    public void publishProductCreatedEvent(ProductCreatedEvent event) {
        try {
            // Set event metadata
            String correlationId = event.getCorrelationId() != null ? event.getCorrelationId()
                    : UUID.randomUUID().toString();
            event.setCorrelationId(correlationId);
            event.setTimestamp(Instant.now().toString());
            event.setSource("product-service");

            // Create producer record with headers
            ProducerRecord<String, Object> record = new ProducerRecord<>(
                    productEventsTopic,
                    event.getProductId().toString(),
                    event);

            // Add headers as per PRD requirements
            record.headers().add(new RecordHeader("correlationId", correlationId.getBytes(StandardCharsets.UTF_8)));
            record.headers().add(new RecordHeader("timestamp", event.getTimestamp().getBytes(StandardCharsets.UTF_8)));
            record.headers().add(new RecordHeader("source", "product-service".getBytes(StandardCharsets.UTF_8)));
            record.headers().add(new RecordHeader("eventType", "ProductCreated".getBytes(StandardCharsets.UTF_8)));

            // Send to Kafka
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(record);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("ProductCreatedEvent published successfully for productId={}, correlationId={}, offset={}",
                            event.getProductId(), correlationId, result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to publish ProductCreatedEvent for productId={}, correlationId={}",
                            event.getProductId(), correlationId, ex);
                }
            });
        } catch (Exception e) {
            log.error("Error publishing ProductCreatedEvent for productId={}", event.getProductId(), e);
        }
    }
}

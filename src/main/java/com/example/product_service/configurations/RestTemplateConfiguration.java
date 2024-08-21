package com.example.product_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {
        /* This is how we can use a different request factory
         restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
         */
        return new RestTemplate();
    }
}

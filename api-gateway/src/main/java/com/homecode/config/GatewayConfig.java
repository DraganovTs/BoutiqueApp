package com.homecode.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customer-service", r -> r.path("api/customers/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("customer-service")
                                .setFallbackUri("forward:/fallback/customers")
                        ))
                        .uri("http://localhost:8092"))
                .route("order-service", r -> r.path("api/orders/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("order-service")
                                .setFallbackUri("forward:/fallback/orders")
                        ))
                        .uri("http://localhost:8091"))
                .route("product-service", r -> r.path("api/products/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("product-service")
                                .setFallbackUri("forward:/fallback/products")
                        ))
                        .uri("http://localhost:8090"))
                .build();
    }


}

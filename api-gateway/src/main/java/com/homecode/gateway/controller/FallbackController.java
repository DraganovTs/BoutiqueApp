package com.homecode.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/customers")
    public String customerServiceFallback() {
        System.out.println("Fallback invoked for customer service");
        return "Fallback response for customer service";
    }

    @GetMapping("/fallback/orders")
    public String orderServiceFallback() {
        return "Fallback response for order service";
    }

    @GetMapping("/fallback/products")
    public String productServiceFallback() {
        return "Fallback response for product service";
    }

    @GetMapping("/fallback/auth")
    public String authServiceFallback() {
        return "Fallback response for auth service";
    }

}

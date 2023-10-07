package com.homecode.product.exception;

import lombok.Data;

@Data
public class CustomCategoryNotFoundException extends RuntimeException {

    private String errorCode;

    public CustomCategoryNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
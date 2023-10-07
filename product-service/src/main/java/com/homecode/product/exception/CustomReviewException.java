package com.homecode.product.exception;

import lombok.Data;

@Data
public class CustomReviewException extends RuntimeException {

    private String errorCode;

    public CustomReviewException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

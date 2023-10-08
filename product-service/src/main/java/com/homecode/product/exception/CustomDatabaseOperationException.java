package com.homecode.product.exception;

import lombok.Data;

@Data
public class CustomDatabaseOperationException extends RuntimeException {

    private String errorCode;

    public CustomDatabaseOperationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

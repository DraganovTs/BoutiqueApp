package com.homecode.product.exception;

import lombok.Data;

@Data
public class NoDataFoundException extends RuntimeException {

    private String errorCode;

    public NoDataFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
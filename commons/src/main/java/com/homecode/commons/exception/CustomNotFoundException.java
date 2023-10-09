package com.homecode.commons.exception;

import lombok.Data;

@Data
public class CustomNotFoundException extends RuntimeException {

    private String errorCode;

    public CustomNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
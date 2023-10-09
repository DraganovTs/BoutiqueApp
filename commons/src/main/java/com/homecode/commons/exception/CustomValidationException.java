package com.homecode.commons.exception;

import lombok.Data;

@Data
public class CustomValidationException extends RuntimeException {

    private String errorCode;

    public CustomValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

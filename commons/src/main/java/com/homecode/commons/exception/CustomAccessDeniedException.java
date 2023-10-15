package com.homecode.commons.exception;

import lombok.Data;

@Data
public class CustomAccessDeniedException extends RuntimeException {
    private String errorCode;

    public CustomAccessDeniedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

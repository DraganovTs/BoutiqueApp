package com.homecode.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)

@Data
public class CustomValidationException extends RuntimeException{
    private String errorCode;

    public CustomValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
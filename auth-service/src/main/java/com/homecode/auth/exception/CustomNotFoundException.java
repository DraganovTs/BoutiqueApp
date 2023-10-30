package com.homecode.auth.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)

@Data
public class CustomNotFoundException extends RuntimeException{
    private String errorCode;

    public CustomNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

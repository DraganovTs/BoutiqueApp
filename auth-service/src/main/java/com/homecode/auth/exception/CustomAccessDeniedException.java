package com.homecode.auth.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomAccessDeniedException extends RuntimeException {
    private String errorCode;

    public CustomAccessDeniedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

package com.homecode.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomAlreadyExistException extends RuntimeException{
    private String errorCode;

    public CustomAlreadyExistException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

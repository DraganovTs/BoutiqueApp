package com.homecode.order.exception;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomDatabaseOperationException extends RuntimeException{
    private String errorCode;

    public CustomDatabaseOperationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

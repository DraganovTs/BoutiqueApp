package com.homecode.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)

@Data
public class CustomBadCredentialException extends RuntimeException{
    private String ErrorCode;

    public CustomBadCredentialException(String message, String errorCode) {
        super(message);
        ErrorCode = errorCode;
    }
}

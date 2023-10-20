package com.homecode.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomIllegalStateException extends RuntimeException {
    private String errorCode;

    public CustomIllegalStateException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

package com.homecode.commons.exception;

import lombok.Data;

@Data
public class CustomAlreadyExistException extends RuntimeException {

   private String errorCode;

    public CustomAlreadyExistException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

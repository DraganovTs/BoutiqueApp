package com.homecode.product.exception;

import lombok.Data;

@Data
public class CustomCategoryExistException extends RuntimeException {

   private String errorCode;

    public CustomCategoryExistException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

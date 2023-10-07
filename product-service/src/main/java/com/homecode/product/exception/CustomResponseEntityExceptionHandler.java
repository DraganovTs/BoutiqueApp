package com.homecode.product.exception;

import com.homecode.product.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(CustomCategoryNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handCategoryResponse
            (CustomCategoryNotFoundException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomCategoryExistException.class)
    protected ResponseEntity<ErrorResponse> handCategoryResponse
            (CustomCategoryExistException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
    }


}

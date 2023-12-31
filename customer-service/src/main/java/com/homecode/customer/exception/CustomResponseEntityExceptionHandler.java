package com.homecode.customer.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleCustomNotFoundResponse
            (CustomNotFoundException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleCustomAlreadyExistResponse
            (CustomAlreadyExistException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CustomDatabaseOperationException.class)
    protected ResponseEntity<ErrorResponse> handleCustomDatabaseOperationResponse
            (CustomDatabaseOperationException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomValidationException.class)
    protected ResponseEntity<ErrorResponse> handleCustomValidationResponse
            (CustomValidationException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomIllegalStateException.class)
    protected ResponseEntity<ErrorResponse> handleCustomIllegalStateResponse
            (CustomIllegalStateException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }


}

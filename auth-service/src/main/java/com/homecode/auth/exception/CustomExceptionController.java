package com.homecode.auth.exception;



import com.homecode.commons.module.exception.CustomAccessDeniedException;
import com.homecode.commons.module.exception.CustomBadCredentialException;
import com.homecode.commons.module.exception.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(CustomBadCredentialException.class)
    protected ResponseEntity<ErrorResponse> handleCustomNotFoundResponse
            (CustomBadCredentialException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleCustomNotFoundResponse
            (CustomAccessDeniedException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getMessage())
                .build(), HttpStatus.FORBIDDEN);
    }
}

package com.homecode.gateway.exception;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(MissingAuthorizationException.class)
    public ResponseEntity<String> handleMissingAuthorizationException(MissingAuthorizationException ex) {
        return ResponseEntity.status(400).body("Missing Authorization: " + ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(401).body("Unauthorized Access: " + ex.getMessage());
    }

}

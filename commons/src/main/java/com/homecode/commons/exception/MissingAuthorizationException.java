package com.homecode.commons.exception;

public class MissingAuthorizationException extends RuntimeException{
    public MissingAuthorizationException(String message) {
        super(message);
    }

}

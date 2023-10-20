package com.homecode.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)

@Data
public class MissingAuthorizationException extends RuntimeException{
    public MissingAuthorizationException(String message) {
        super(message);
    }

}

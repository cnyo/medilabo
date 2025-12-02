package com.medilabo.front.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedFrontException extends RuntimeException {
    public UnauthorizedFrontException(String message) {
        super(message);
    }
}

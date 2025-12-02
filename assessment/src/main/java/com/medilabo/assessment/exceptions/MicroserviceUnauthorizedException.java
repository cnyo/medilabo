package com.medilabo.assessment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MicroserviceUnauthorizedException extends RuntimeException {
    public MicroserviceUnauthorizedException(String message) {
        super(message);
    }
}

package com.medilabo.assessment.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class AssessmentErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoke, Response response) {
        if(response.status() == 401 ) {
            return new MicroserviceUnauthorizedException("Authentification requise.");
        }

        return defaultErrorDecoder.decode(invoke, response);
    }
}

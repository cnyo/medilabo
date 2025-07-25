package com.medilabo.front.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String Summoner, Response response) {
        if (response.status() == 404) {
            return new PatientNotFoundException("Patient not found");
        }

        return defaultErrorDecoder.decode(Summoner, response);
    }
}

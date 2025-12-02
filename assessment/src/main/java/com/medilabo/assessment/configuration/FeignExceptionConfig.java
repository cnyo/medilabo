package com.medilabo.assessment.configuration;

import com.medilabo.assessment.exceptions.AssessmentErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to handle exceptions from Feign clients.
 * It defines a custom error decoder to process error responses from microservices.
 */
@Configuration
public class FeignExceptionConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignExceptionConfig.class);

    /**
     * Bean definition for the custom AssessmentErrorDecoder.
     * This decoder will be used by Feign clients to handle error responses.
     */
    @Bean
    public AssessmentErrorDecoder feignExceptionDecoder() {
        log.debug("Initializing FeignExceptionConfig with AssessmentErrorDecoder");

        return new AssessmentErrorDecoder();
    }
}

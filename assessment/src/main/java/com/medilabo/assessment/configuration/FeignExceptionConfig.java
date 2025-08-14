package com.medilabo.assessment.configuration;

import com.medilabo.assessment.exceptions.AssessmentErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExceptionConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignExceptionConfig.class);

    @Bean
    public AssessmentErrorDecoder feignExceptionDecoder() {
        log.debug("Initializing FeignExceptionConfig with AssessmentErrorDecoder");

        return new AssessmentErrorDecoder();
    }
}

package com.medilabo.assessment.configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthPropagationConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignExceptionConfig.class);

    @Bean
    public RequestInterceptor propagateAuthHeader() {
        return new RequestInterceptor() {
            @Override
            public void apply(feign.RequestTemplate template) {
                log.debug("Initializing FeignExceptionConfig with AssessmentErrorDecoder");
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attrs != null) {
                    HttpServletRequest request = attrs.getRequest();
                    String authHeader = request.getHeader("Authorization");
                    log.debug("Authorization header: {}", authHeader);

                    if (authHeader != null && !authHeader.isEmpty()) {
                        template.header("Authorization", authHeader);
                        log.debug("Authorization header propagated to Feign request");
                    }
                }
            }
        };
    }
}

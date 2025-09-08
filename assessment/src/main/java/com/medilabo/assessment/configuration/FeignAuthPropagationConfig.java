package com.medilabo.assessment.configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Configuration class to propagate the Authorization header from incoming requests
 * to outgoing Feign client requests.
 */
@Configuration
public class FeignAuthPropagationConfig {
    private static final Logger log = LoggerFactory.getLogger(FeignAuthPropagationConfig.class);

    /** Interceptor to add the Authorization header from the current HTTP request
     * to the Feign request template.
     */
    @Bean
    public RequestInterceptor propagateAuthHeader() {
        /** RequestInterceptor is a functional interface, so we can use a lambda expression here.
         * However, for clarity and logging purposes, we use an anonymous class.
         */
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

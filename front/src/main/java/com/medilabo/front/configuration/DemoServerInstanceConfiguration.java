package com.medilabo.front.configuration;

import com.medilabo.front.instance.DemoInstanceSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoServerInstanceConfiguration {
    @Bean
    ServiceInstanceListSupplier ServiceInstanceListSupplier() {
        return new DemoInstanceSupplier("localhost");
    }
}

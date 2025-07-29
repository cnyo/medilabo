package com.medilabo.patient.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties("medilabo")
public class ApplicationPropertiesConfiguration {
    private int patientsLimit;

    public int getPatientsLimit() {
        return patientsLimit;
    }

    public void setPatientsLimit(int patientsLimit) {
        this.patientsLimit = patientsLimit;
    }
}

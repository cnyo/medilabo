package com.medilabo.assessment.enums;

/**
 * Enum representing different levels of health risk.
 * Each level is associated with a descriptive label.
 */
public enum RiskLevel {
    NONE("Aucun risque"),
    BORDERLINE("Risque limité"),
    IN_DANGER("Danger"),
    EARLY_ONSET("Apparition précoce");

    private final String label;

    RiskLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

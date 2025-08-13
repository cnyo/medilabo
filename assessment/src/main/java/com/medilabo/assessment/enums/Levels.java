package com.medilabo.assessment.enums;

public enum Levels {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early onset");

    private final String label;

    Levels(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

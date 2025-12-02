package com.medilabo.assessment.enums;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

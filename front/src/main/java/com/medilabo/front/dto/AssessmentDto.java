package com.medilabo.front.dto;

public class AssessmentDto {
    private int patientId;
    private String riskLevel;

    public AssessmentDto() {
    }

    public AssessmentDto(int patientId, String riskLevel) {
        this.patientId = patientId;
        this.riskLevel = riskLevel;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}

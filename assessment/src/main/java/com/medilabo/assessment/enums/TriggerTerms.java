package com.medilabo.assessment.enums;

public enum TriggerTerms {
    HEMOGLOBUNE_A1C("Hémoglobine A1C"),
    MICROALBUMINE("Microalbumine"),
    TAILLE("Taille"),
    FUMEUR("Fumeur"),
    FUMEUSE("Fumeuse"),
    ANORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGES("Vertiges"),
    RECHUTE("Rechute"),
    REACTION("Réaction"),
    ANTICORPS("Anticorps");

    private final String label;

    TriggerTerms(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

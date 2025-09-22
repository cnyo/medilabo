package com.medilabo.assessment.enums;

/**
 * Enum representing various medical trigger terms.
 * Each term is associated with a descriptive label.
 */
public enum TriggerTerm {
    HEMOGLOBUNE_A1C("Hémoglobine A1C"),
    MICROALBUMINE("Microalbumine"),
    TAILLE("Taille"),
    POIDS("Poids"),
    FUMEUR("Fumeur"),
    FUMEUSE("Fumeuse"),
    ANORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGE("Vertige"),
    VERTIGES("Vertiges"),
    RECHUTE("Rechute"),
    REACTION("Réaction"),
    ANTICORPS("Anticorps");

    private final String label;

    TriggerTerm(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

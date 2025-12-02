package com.medilabo.assessment.rules;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.proxies.patient.PatientDto;

import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * Represents a rule for determining the risk level of a patient based on certain conditions.
 * Each rule consists of:
 * - A predicate to evaluate patient attributes
 * - A predicate to evaluate the count of trigger terms found in patient notes.
 * - An associated risk level if both predicates are satisfied.
 */
public class LevelRule {
    private Predicate<PatientDto> patientCondition;
    private LongPredicate countTermPredicate;
    private RiskLevel level;

    public LevelRule(Predicate<PatientDto> patientCondition, LongPredicate countTermPredicate, RiskLevel level) {
        this.patientCondition = patientCondition;
        this.countTermPredicate = countTermPredicate;
        this.level = level;
    }

    public boolean matches(PatientDto patient, long countFoundTerms) {
        return patientCondition.test(patient) && countTermPredicate.test(countFoundTerms);
    }

    public RiskLevel getLevel() {
        return level;
    }
}

package com.medilabo.assessment.services;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.enums.TriggerTerm;
import com.medilabo.assessment.proxies.note.NoteDto;
import com.medilabo.assessment.proxies.patient.PatientDto;
import com.medilabo.assessment.rules.LevelRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Service class to handle health risk assessments based on patient data and notes.
 * It processes the assessment by counting trigger terms in notes and determining the risk level.
 */
@Service
public class AssessmentService {

    private static final Logger log = LoggerFactory.getLogger(AssessmentService.class);

    private static final List<Pattern> TRIGGER_TERMS = Arrays.stream(TriggerTerm.values())
            .map(term -> Pattern.compile(Pattern.quote(term.getLabel().toLowerCase())))
            .toList();

    private static final List<LevelRule> RULES = List.of(
            new LevelRule(p -> p.getAge() > 30, count -> count >= 2 && count <= 5, RiskLevel.BORDERLINE),
            new LevelRule(p -> p.isMale() && p.getAge() < 30, count -> count == 3, RiskLevel.IN_DANGER),
            new LevelRule(p -> p.isFemale() && p.getAge() < 30, count -> count == 4, RiskLevel.IN_DANGER),
            new LevelRule(p -> p.getAge() >= 30, count -> count == 6 || count == 7, RiskLevel.IN_DANGER),
            new LevelRule(p -> p.isMale() && p.getAge() < 30, count -> count >= 5, RiskLevel.EARLY_ONSET),
            new LevelRule(p -> p.isFemale() && p.getAge() < 30, count -> count >= 7, RiskLevel.EARLY_ONSET),
            new LevelRule(p -> p.getAge() >= 30, count -> count >= 8, RiskLevel.EARLY_ONSET)
    );

    private final ResourceUrlProvider resourceUrlProvider;

    public AssessmentService(ResourceUrlProvider resourceUrlProvider) {
        this.resourceUrlProvider = resourceUrlProvider;
    }

    /**
     * Processes the health risk assessment for a given patient based on their notes.
     *
     * @param patient The patient data transfer object containing patient details.
     * @param notes   A list of note data transfer objects associated with the patient.
     * @return A String representing the determined risk level.
     */
    public String processAssessment(PatientDto patient, List<NoteDto> notes) {
        log.debug("Processing assessment for patient: {}", String.valueOf(patient.getId()));
        long countFoundTerms = countTriggerTerms(notes);

        return determineRiskLevel(patient, countFoundTerms);
    }

    /**
     * Counts the occurrences of predefined trigger terms in a list of patient notes.
     *
     * @param notes A list of note data transfer objects associated with the patient.
     * @return The total count of trigger term occurrences found in the notes.
     */
    public long countTriggerTerms(List<NoteDto> notes) {
        log.debug("Counting trigger terms for patient: {}", String.valueOf(notes.get(0).getId()));

        return notes.stream()
                .map(NoteDto::getNote)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .flatMapToLong(noteContent ->
                        TRIGGER_TERMS.stream().mapToLong(termPattern -> countOccurrences(noteContent, termPattern))
                )
                .sum()
        ;
    }

    /**
     * Counts the occurrences of a specific trigger term pattern in a given note content.
     *
     * @param noteContent The content of the note to be analyzed.
     * @param termPattern The regex pattern representing the trigger term to be counted.
     * @return The count of occurrences of the trigger term in the note content.
     */
    private long countOccurrences(String noteContent, Pattern termPattern) {
        log.debug("Counting occurrences for patient: {}", String.valueOf(noteContent));

        return termPattern
                .matcher(noteContent)
                .results()
                .count();
    }

    /**
     * Determines the risk level for a patient based on their details and the count of found trigger terms.
     *
     * @param patient         The patient data transfer object containing patient details.
     * @param countFoundTerms The count of trigger terms found in the patient's notes.
     * @return A String representing the determined risk level.
     */
    private String determineRiskLevel(PatientDto patient, long countFoundTerms) {
        log.debug("Determining risk level for patient: {}", String.valueOf(patient.getId()));

        return RULES.stream()
                .filter(rule -> rule.matches(patient, countFoundTerms))
                .map(rule -> rule.getLevel().getLabel())
                .findFirst()
                .orElse(RiskLevel.NONE.getLabel());
    }
}

package com.medilabo.assessment.services;

import com.medilabo.assessment.enums.TriggerTermEnum;
import com.medilabo.assessment.proxies.note.NoteDto;
import com.medilabo.assessment.proxies.patient.PatientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AssessmentService {

    private static final Logger log = LoggerFactory.getLogger(AssessmentService.class);

    public String processAssessment(PatientDto patient, List<NoteDto> notes) {
        System.out.println("Processing assessment...");
        log.debug("Processing assessment for patient: {}", String.valueOf(patient.getId()));

        long countFoundTerms = countTriggerTerms(notes);

        String level = "None";

        if (level.equals("Borderline")) {
            return "risque limité (Borderline) : Le dossier du patient contient entre deux et cinq déclencheurs et le patient est âgé de plus de 30 ans";
        }

        if (level.equals("In Danger")) {
            return "danger (In Danger) : Dépend de l'âge et du sexe du patient. Si le patient est un homme de moins de 30 ans, alors trois termes déclencheurs doivent être présents. Si le patient est une femme et a moins de 30 ans, il faudra quatre termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra six ou sept ";
        }

        if (level.equals("Early onset")) {
            return "apparition précoce (Early onset) : Encore une fois, cela dépend de l'âge et du sexe. Si le patient est un homme de moins de 30 ans, alors au moins cinq termes déclencheurs sont nécessaires. Si le patient est une femme et a moins de 30 ans, il faudra au moins sept termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra huit ou plus.";
        }

        return "aucun risque (None) : Le dossier du patient ne contient aucune note du médecin contenant les déclencheurs (terminologie)";
    }

    private long countTriggerTerms(List<NoteDto> notes) {

        return notes.stream()
                .map(note -> note.getNote().toLowerCase())
                .flatMapToLong(noteText ->
                        Arrays.stream(TriggerTermEnum.values())
                                .map(trigger -> Pattern.quote(trigger.getLabel().toLowerCase()))
                                .mapToLong(term -> countOccurrences(noteText, term))
                )
                .sum();
    }

    private long countOccurrences(String noteText, String term) {
        return Pattern.compile(noteText)
                .matcher(term)
                .results()
                .count();
    }
}

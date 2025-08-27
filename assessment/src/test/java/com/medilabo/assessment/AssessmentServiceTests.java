package com.medilabo.assessment;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.model.Assessment;
import com.medilabo.assessment.proxies.note.NoteDto;
import com.medilabo.assessment.proxies.patient.PatientDto;
import com.medilabo.assessment.services.AssessmentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTests {

	private final AssessmentService assessmentService = new AssessmentService();

	@ParameterizedTest
	@MethodSource("provideRiskCases")
	void determineRiskLevel_shouldReturnExpectedLevel(int age, String gender, List<NoteDto> notes, String riskLevelExpected) {
		// Arrange
		PatientDto patient = new PatientDto();
		patient.setGender(gender);
		patient.setName("Patient1");
		patient.setFirstName("anyFirstName");
		patient.setBirthDate(LocalDate.now().minusYears(age).withMonth(1).withDayOfMonth(1));

		// Act
		Assessment result = assessmentService.processAssessment(patient, notes);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(Assessment.class);
		assertThat(result.getRiskLevel()).isEqualTo(riskLevelExpected);
	}

	static Stream<Arguments> provideRiskCases() {
		NoteDto noteWithNoneTerm = new NoteDto("5", "2", "Patient1", "Pas de mot clef.");
		NoteDto noteWith1Term = new NoteDto("5", "2", "Patient1", "Anticorps.");
		NoteDto noteWith2Terms = new NoteDto("5", "2", "Patient1", "anormal et Anticorps.");
		NoteDto noteWith3Terms = new NoteDto("5", "2", "Patient1", "Rechute, anormal et Anticorps.");
		NoteDto noteWith4Terms = new NoteDto("5", "2", "Patient1", "fumeur, Rechute, anormal et Anticorps.");
		NoteDto noteWith5Terms = new NoteDto("5", "2", "Patient1", "Taille, fumeur, Rechute, anormal et Anticorps.");
		NoteDto noteWith6Terms = new NoteDto("5", "2", "Patient1", "Réaction, Taille, fumeur, Rechute, anormal et Anticorps.");
		NoteDto noteWith7Terms = new NoteDto("5", "2", "Patient1", "Hémoglobine A1C, Réaction, Taille, fumeur, Rechute, anormal et Anticorps.");
		NoteDto noteWithMore8Terms = new NoteDto("5", "2", "Patient1", "Anticorps, Microalbumine, Hémoglobine A1C, Réaction, Taille, fumeur, Rechute, anormal et Anticorps.");

		return Stream.of(
				// --- BORDERLINE ---
				Arguments.of(31, "M", List.of(noteWith2Terms), RiskLevel.BORDERLINE.getLabel()),
				Arguments.of(45, "F", List.of(noteWith5Terms), RiskLevel.BORDERLINE.getLabel()),

				// --- IN_DANGER ---
				Arguments.of(25, "M",  List.of(noteWith3Terms), RiskLevel.IN_DANGER.getLabel()), // male <30, 3 terms
				Arguments.of(28, "F", List.of(noteWith4Terms), RiskLevel.IN_DANGER.getLabel()), // female <30, ==4 terms
				Arguments.of(40, "M",  List.of(noteWith6Terms), RiskLevel.IN_DANGER.getLabel()), // age >=30, 6 terms
				Arguments.of(30, "F", List.of(noteWith7Terms), RiskLevel.IN_DANGER.getLabel()),

				// --- EARLY_ONSET ---
				Arguments.of(25, "M",  List.of(noteWith5Terms), RiskLevel.EARLY_ONSET.getLabel()), // male <30, >=5 terms
				Arguments.of(29, "F", List.of(noteWith7Terms), RiskLevel.EARLY_ONSET.getLabel()), // female <30, >=7 terms
				Arguments.of(50, "M",  List.of(noteWithMore8Terms), RiskLevel.EARLY_ONSET.getLabel()), // age >=30, >=8 terms
				Arguments.of(48, "F",  List.of(noteWithMore8Terms), RiskLevel.EARLY_ONSET.getLabel()), // age >=30, >=8 terms

				// --- NONE ---
				Arguments.of(20, "M",  List.of(noteWith1Term), RiskLevel.NONE.getLabel()),
				Arguments.of(35, "F", List.of(noteWithNoneTerm), RiskLevel.NONE.getLabel())
		);
	}
}

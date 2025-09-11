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
	void determineRiskLevel_shouldReturnExpectedLevel(int age, String gender, int triggerCount, String riskLevelExpected) {
		// Arrange
		PatientDto patient = new PatientDto();
		patient.setGender(gender);
		patient.setName("Patient1");
		patient.setFirstName("anyFirstName");
		patient.setBirthDate(LocalDate.now().minusYears(age).withMonth(1).withDayOfMonth(1));

		// Act
		Assessment result = assessmentService.processAssessment(patient, triggerCount);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isInstanceOf(Assessment.class);
		assertThat(result.getRiskLevel()).isEqualTo(riskLevelExpected);
	}

	static Stream<Arguments> provideRiskCases() {
		return Stream.of(
				// --- BORDERLINE ---
				Arguments.of(31, "M", 2, RiskLevel.BORDERLINE.getLabel()),
				Arguments.of(45, "F", 5, RiskLevel.BORDERLINE.getLabel()),

				// --- IN_DANGER ---
				Arguments.of(25, "M", 3, RiskLevel.IN_DANGER.getLabel()), // male <30, 3 terms
				Arguments.of(28, "F", 4, RiskLevel.IN_DANGER.getLabel()), // female <30, ==4 terms
				Arguments.of(40, "M", 6, RiskLevel.IN_DANGER.getLabel()), // age >=30, 6 terms
				Arguments.of(30, "F", 7, RiskLevel.IN_DANGER.getLabel()),

				// --- EARLY_ONSET ---
				Arguments.of(25, "M", 5, RiskLevel.EARLY_ONSET.getLabel()), // male <30, >=5 terms
				Arguments.of(29, "F", 7, RiskLevel.EARLY_ONSET.getLabel()), // female <30, >=7 terms
				Arguments.of(50, "M", 8, RiskLevel.EARLY_ONSET.getLabel()), // age >=30, >=8 terms
				Arguments.of(48, "F", 10, RiskLevel.EARLY_ONSET.getLabel()), // age >=30, >=8 terms

				// --- NONE ---
				Arguments.of(20, "M", 1, RiskLevel.NONE.getLabel()),
				Arguments.of(35, "F", 0, RiskLevel.NONE.getLabel())
		);
	}
}

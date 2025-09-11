package com.medilabo.assessment.tree;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.proxies.patient.PatientDto;

/**
 * Represents the entire decision tree for assessing patient risk levels.
 * This class encapsulates the root of the tree and provides methods to evaluate risk,
 * print the tree structure, and gather metrics about the tree.
 */
public class MedicalRiskDecisionTree {
    private final DecisionNode rootNode; // ← Root of the tree

    public MedicalRiskDecisionTree() {
        this.rootNode = buildDecisionTree();
    }

    /**
     * Builds the root decision tree.
     * <p>
     * Splits the logic into two main branches:
     * <ul>
     *   <li>Patients aged 30 or older → {@link #buildOldRules()}</li>
     *   <li>Patients younger than 30 → {@link #buildYoungRules()}</li>
     * </ul>
     * </p>
     *
     * @return The root {@link DecisionNode} of the tree.
     */
    private DecisionNode buildDecisionTree() {
        return DecisionTreeBuilder.ageGreaterOrEquals(30).then(
                buildOldRules(), // ← TRUE branch: patient >= 30 years old
                buildYoungRules() // ← FALSE branch: patient < 30 years old
        );
    }

    /**
     * Builds the decision rules for patients aged 30 or older.
     * <ul>
     *   <li>2–5 triggers → {@link RiskLevel#BORDERLINE}</li>
     *   <li>6–7 triggers → {@link RiskLevel#IN_DANGER}</li>
     *   <li>≥ 8 triggers → {@link RiskLevel#EARLY_ONSET}</li>
     *   <li>0–1 trigger → {@link RiskLevel#NONE}</li>
     * </ul>
     *
     * @return A {@link DecisionNode} for older patients.
     */
    private DecisionNode buildOldRules() {
        return DecisionTreeBuilder.triggerBetween(2, 5).then(
                DecisionTreeBuilder.leaf(RiskLevel.BORDERLINE),    // ← Si 2-5 triggers
                DecisionTreeBuilder.triggerBetween(6, 7).then(     // ← Sinon, teste 6-7
                        DecisionTreeBuilder.leaf(RiskLevel.IN_DANGER), // ← Si 6-7 triggers
                        DecisionTreeBuilder.triggerGreaterOrEqual(8).then(  // ← Sinon, teste >= 8
                                DecisionTreeBuilder.leaf(RiskLevel.EARLY_ONSET), // ← Si >= 8 triggers
                                DecisionTreeBuilder.leaf(RiskLevel.NONE)          // ← Sinon (0-1 trigger)
                        )
                )
        );
    }

    /**
     * Builds the decision rules for patients younger than 30.
     * <p>
     * Branches by gender:
     * <ul>
     *   <li>Male → {@link #buildYoungMaleRules()}</li>
     *   <li>Female → {@link #buildYoungFemaleRules()}</li>
     * </ul>
     * </p>
     *
     * @return A {@link DecisionNode} for younger patients.
     */
    private DecisionNode buildYoungRules() {
        return DecisionTreeBuilder.isMale().then(
                buildYoungMaleRules(), // ← Male < 30 years old
                buildYoungFemaleRules() // ← Male < 30 years old
        );
    }

    /**
     * Builds the decision rules for male patients younger than 30.
     * <ul>
     *   <li>Exactly 3 triggers → {@link RiskLevel#IN_DANGER}</li>
     *   <li>≥ 5 triggers → {@link RiskLevel#EARLY_ONSET}</li>
     *   <li>0–2 or 4 triggers → {@link RiskLevel#NONE}</li>
     * </ul>
     *
     * @return A {@link DecisionNode} for young male patients.
     */
    private DecisionNode buildYoungMaleRules() {
        return DecisionTreeBuilder.triggerEquals(3).then(
                DecisionTreeBuilder.leaf(RiskLevel.IN_DANGER), // ← Si 3 triggers
                DecisionTreeBuilder.triggerGreaterOrEqual(5).then(
                        DecisionTreeBuilder.leaf(RiskLevel.EARLY_ONSET), // ← Si >= 5 triggers
                        DecisionTreeBuilder.leaf(RiskLevel.NONE)          // ← Sinon 0-2 triggers
                )
        );
    }

    /**
     * Builds the decision rules for female patients younger than 30.
     * <ul>
     *   <li>Exactly 4 triggers → {@link RiskLevel#IN_DANGER}</li>
     *   <li>≥ 7 triggers → {@link RiskLevel#EARLY_ONSET}</li>
     *   <li>0–3 or 5–6 triggers → {@link RiskLevel#NONE}</li>
     * </ul>
     *
     * @return A {@link DecisionNode} for young female patients.
     */
    private DecisionNode buildYoungFemaleRules() {
        return DecisionTreeBuilder.triggerEquals(4).then(
                DecisionTreeBuilder.leaf(RiskLevel.IN_DANGER), // ← Si 3 triggers
                DecisionTreeBuilder.triggerGreaterOrEqual(7).then(
                        DecisionTreeBuilder.leaf(RiskLevel.EARLY_ONSET), // ← Si >= 5 triggers
                        DecisionTreeBuilder.leaf(RiskLevel.NONE)          // ← Sinon 0-2 triggers
                )
        );
    }

    // ═══════════════════════════════════════════════════════════
    // 🚀 PUBLIC METHODS OF USE
    // ═══════════════════════════════════════════════════════════

    /**
     * MAIN METHOD: Assesses the level of risk
     * Evaluates the risk level for a given patient based on their data and trigger count.
     *
     * @param patient      The patient data transfer object containing patient details.
     * @param TriggerCount The count of trigger terms found in the patient's notes.
     * @return The determined RiskLevel for the patient.
     */
    public RiskLevel evaluateRisk(PatientDto patient, int TriggerCount) {
        // Delegate to the root of the tree which will navigate recursively
        return rootNode.evaluate(patient, TriggerCount);
    }

    public void printTree() {
        System.out.println("=== ARBRE DÉCISIONNEL MÉDICAL ===");
        System.out.println(rootNode.visualize(0));
        System.out.println("================================");
    }

    /**
     * 📊 METRICS: Tree Information
     *
     * Allows analysis of complexity and performance.
     */
    public TreeMetrics getMetrics() {
        return new TreeMetrics(rootNode);
    }

    /**
     * Getter for direct root access (if needed)
     * @return The root decision node of the tree.
     */
    public DecisionNode getRootNode() {
        return rootNode;
    }
}

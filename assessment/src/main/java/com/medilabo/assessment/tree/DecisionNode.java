package com.medilabo.assessment.tree;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.proxies.patient.PatientDto;

/**
 * Represents a node in the decision tree for patient risk assessment.
 * This can be either a condition node (branch) or a leaf node (final decision).
 */
public interface DecisionNode {

    /**
     * Evaluates the decision node based on patient data and trigger count.
     *
     * @param patient      The patient data transfer object containing patient details.
     * @param triggerCount The count of trigger terms found in the patient's notes.
     * @return The determined RiskLevel based on the evaluation.
     */
    RiskLevel evaluate(PatientDto patient, int triggerCount);

    /**
     * Indicates whether this node is a leaf node (i.e., it represents a final decision).
     *
     * @return true if this node is a leaf node; false otherwise.
     */
    Boolean isLeaf();

    /**
     * Visualizes the decision node and its children for debugging purposes.
     *
     * @param depth The current depth in the tree for indentation purposes.
     * @return A string representation of the decision node and its children.
     */
    default String visualize(int depth) {
        return "  ".repeat(depth) + (isLeaf() ? "Leaf" : "Branch") + "\n";
    }
}

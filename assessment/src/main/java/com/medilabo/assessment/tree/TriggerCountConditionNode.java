package com.medilabo.assessment.tree;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.proxies.patient.PatientDto;

import java.util.function.Predicate;

/**
 * Represents a condition node in the decision tree that evaluates based on the count of trigger terms.
 * This node branches to different decision nodes based on whether the condition is met.
 */
public class TriggerCountConditionNode implements DecisionNode {
    private final Predicate<Integer> condition;
    private final String conditionDescription;
    private final DecisionNode trueNode;
    private final DecisionNode falseNode;

    public TriggerCountConditionNode(Predicate<Integer> condition,
                                     String conditionDescription,
                                     DecisionNode trueNode,
                                     DecisionNode falseNode) {
        this.condition = condition;
        this.conditionDescription = conditionDescription;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    /**
     * Evaluates the decision node based on patient data and trigger count.
     *
     * @param patient      The patient data transfer object containing patient details.
     * @param triggerCount The count of trigger terms found in the patient's notes.
     * @return The determined RiskLevel based on the evaluation.
     */
    @Override
    public RiskLevel evaluate(PatientDto patient, int triggerCount) {
        if (condition.test(triggerCount)) {
            // If the condition is true, evaluate the true branch
            // Condition true → we go right in the tree
            return trueNode.evaluate(patient, triggerCount);
        } else {
            // If the condition is false, evaluate the false branch
            // Condition false → we go left in the tree
            return falseNode.evaluate(patient, triggerCount);
        }
    }

    /**
     * Indicates whether this node is a leaf node (i.e., it represents a final decision).
     *
     * @return false since this is a condition node.
     */
    @Override
    public Boolean isLeaf() {
        return null;
    }

    /**
     * Visualizes the decision node and its children for debugging purposes.
     *
     * @param depth The current depth in the tree for indentation purposes.
     * @return A string representation of the decision node and its children.
     */
    @Override
    public String visualize(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(depth)).append("Triggers ").append(conditionDescription).append(" ?\n");
        sb.append("  ".repeat(depth + 1)).append("├─ OUI:\n");
        sb.append(trueNode.visualize(depth + 2)).append("\n");
        sb.append("  ".repeat(depth + 1)).append("└─ NON:\n");
        sb.append(falseNode.visualize(depth + 2));
        return sb.toString();
    }
}

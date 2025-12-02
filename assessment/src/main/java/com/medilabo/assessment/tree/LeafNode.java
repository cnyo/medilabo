package com.medilabo.assessment.tree;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.proxies.patient.PatientDto;

/**
 * Represents a leaf node in the decision tree, which holds a final risk level decision.
 */
public class LeafNode implements DecisionNode {
    private final RiskLevel riskLevel;

    public LeafNode(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    /** Evaluates the leaf node and returns its associated risk level. */
    @Override
    public RiskLevel evaluate(PatientDto patient, int triggerCount) {
        // As a leaf node, simply return the associated risk level.
        return riskLevel;
    }

    /** A leaf node is a terminal node in the decision tree. */
    @Override
    public Boolean isLeaf() {
        // This is indeed a leaf node.
        return true;
    }

    /** For debugging purposes */
    @Override
    public String visualize(int depth) {
        return "  ".repeat(depth) + "->: " + riskLevel.name();
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
}

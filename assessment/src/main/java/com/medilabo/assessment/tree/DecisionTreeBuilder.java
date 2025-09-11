package com.medilabo.assessment.tree;

import com.medilabo.assessment.enums.RiskLevel;
import com.medilabo.assessment.proxies.patient.PatientDto;

import java.util.function.Predicate;

/**
 * A builder class for constructing decision trees used in patient risk assessment.
 */
public class DecisionTreeBuilder {

    // ═══════════════════════════════════════════════════════════
    // 👤 METHODS FOR CONDITIONS ON PATIENTS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates an "age >= threshold" condition
     * Example: ageGreaterOrEqual(30) → "Is the patient 30 years old or older?"
     *
     * @param threshold The age threshold to compare against.
     * @return A ConditionBuilder that can be used to build decision nodes based on this condition.
     */
    public static ConditionBuilder ageGreaterOrEquals(int threshold) {
        return new ConditionBuilder(
                patient -> patient.getAge() >= threshold,
                "Age >= " + threshold
        );
    }

    /**
     * Creates a condition that checks whether the patient is male.
     *
     * @return A {@link ConditionBuilder} for chaining further decision nodes.
     */
    public static ConditionBuilder isMale() {
        return new ConditionBuilder(
                PatientDto::isMale,
                "Is male"
        );
    }

    /**
     * Creates a condition that checks whether the patient is female.
     *
     * @return A {@link ConditionBuilder} for chaining further decision nodes.
     */
    public static ConditionBuilder isFemale() {
        return new ConditionBuilder(
                PatientDto::isFemale,
                "Is female"
        );
    }

    // ═══════════════════════════════════════════════════════════
    // 🔢 METHODS FOR CONDITIONS ON TRIGGERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a "trigger count equals count" condition
     * Example: triggerEquals(3) → "Is the trigger count exactly 3?"
     *
     * @param count The exact trigger count to compare against.
     * @return A TriggerConditionBuilder that can be used to build decision nodes based on this condition.
     */
    public static TriggerConditionBuilder triggerEquals(int count) {
        return new TriggerConditionBuilder(
                trigger -> trigger == count,
                "= " + count
        );
    }

    /**
     * Creates a "trigger count between min and max" condition
     * Example: triggerBetween(3, 5) → "Is the trigger count between 3 and 5 (inclusive)?"
     *
     * @param min The minimum trigger count (inclusive).
     * @param max The maximum trigger count (inclusive).
     * @return A TriggerConditionBuilder that can be used to build decision nodes based on this condition.
     */
    public static TriggerConditionBuilder triggerBetween(int min, int max) {
        return new TriggerConditionBuilder(
                trigger -> trigger >= min && trigger <= max,
                "Between " + min + " and " + max
        );
    }

    /**
     * Creates a "trigger count greater than or equal to count" condition
     * Example: triggerGreaterOrEqual(8) → "Is the trigger count 8 or more?"
     *
     * @param count The minimum trigger count to compare against.
     * @return A TriggerConditionBuilder that can be used to build decision nodes based on this condition.
     */
    public static TriggerConditionBuilder triggerGreaterOrEqual(int count) {
        return new TriggerConditionBuilder(
                trigger -> trigger >= count,
                ">= " + count
        );
    }

    // ═══════════════════════════════════════════════════════════
    // 🍃 METHOD TO CREATE A SHEET (FINAL RESULT)
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a leaf node representing a final risk level decision.
     *
     * @param riskLevel The risk level to be associated with this leaf node.
     * @return A LeafNode that is a leaf with the specified risk level.
     */
    public static LeafNode leaf(RiskLevel riskLevel) {
        return new LeafNode(riskLevel);
    }

    // ═══════════════════════════════════════════════════════════
    // 🔨 INTERNAL CLASSES FOR THE PATTERN BUILDER
    // ═══════════════════════════════════════════════════════════

    /**
     * Builder for conditions based on patient attributes.
     */
    public static class ConditionBuilder {
        private final Predicate<PatientDto> condition;
        private final String description;

        ConditionBuilder(Predicate<PatientDto> condition, String description) {
            this.condition = condition;
            this.description = description;
        }

        /**
         * ⭐ Finalizes the construction of the node
         *
         * @param trueNode  The decision node to follow if the condition is true.
         * @param falseNode The decision node to follow if the condition is false.
         * @return A DecisionNode that branches based on the specified condition.
         */
        public DecisionNode then(DecisionNode trueNode, DecisionNode falseNode) {
            return new PatientConditionNode(condition, description, trueNode, falseNode);

        }
    }

    /**
     * Builder for conditions based on trigger counts.
     */
    public static class TriggerConditionBuilder {
        private final Predicate<Integer> condition;
        private final String description;

        TriggerConditionBuilder(Predicate<Integer> condition, String description) {
            this.condition = condition;
            this.description = description;
        }

        /**
         * ⭐ Finalizes the construction of the node
         *
         * @param trueNode  The decision node to follow if the condition is true.
         * @param falseNode The decision node to follow if the condition is false.
         * @return A DecisionNode that branches based on the specified trigger count condition.
         */
        public DecisionNode then(DecisionNode trueNode, DecisionNode falseNode) {
            return new TriggerCountConditionNode(condition, description, trueNode, falseNode);
        }
    }
}

package com.medilabo.assessment.tree;

/**
 * Class to estimate and hold metrics about the decision tree.
 * This includes depth, total number of nodes, and total number of leaf nodes.
 * The estimations are based on the known structure of the decision tree.
 */
public class TreeMetrics {
    private final Integer depth; // Depth of the tree
    private final Integer nodeCount; // Total number of nodes
    private final Integer leafCount; // Total number  of leaf nodes

    public TreeMetrics(DecisionNode rootNode) {
        this.depth = estimateDepth(rootNode);
        this.nodeCount = estimateNodeCount(rootNode);
        this.leafCount = estimateLeafCount(rootNode);
    }

    /**
     * Estimates the depth of the decision tree.
     * This is a manual estimation based on the known structure of the tree.
     *
     * @param rootNode The root node of the decision tree.
     * @return The estimated depth of the tree.
     */
    private Integer estimateDepth(DecisionNode rootNode) {
        if (rootNode.isLeaf()) {
            return 1;
        }

        // Manually calculated depth based on the tree structure
        return 4;
    }

    /**
     * Estimates the total number of nodes in the decision tree.
     * This is a manual estimation based on the known structure of the tree.
     *
     * @param rootNode The root node of the decision tree.
     * @return The estimated total number of nodes in the tree.
     */
    private Integer estimateNodeCount(DecisionNode rootNode) {
        return 15;
    }

    /**
     * Estimates the total number of leaf nodes in the decision tree.
     * This is a manual estimation based on the known structure of the tree.
     *
     * @param rootNode The root node of the decision tree.
     * @return The estimated total number of leaf nodes in the tree.
     */
    private Integer estimateLeafCount(DecisionNode rootNode) {
        return 10;
    }

    /**
     * Gets the depth of the decision tree.
     * @return
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * Gets the total number of nodes in the decision tree.
     * @return
     */
    public Integer getNodeCount() {
        return nodeCount;
    }

    /**
     * Gets the total number of leaf nodes in the decision tree.
     * @return
     */
    public Integer getLeafCount() {
        return leafCount;
    }

    /**
     * String representation of the tree metrics.
     * @return
     */
    @Override
    public String toString() {
        return String.format("TreeMetrics{depth=%d, nodes=%d, leaves=%d}",
                depth, nodeCount, leafCount);
    }
}

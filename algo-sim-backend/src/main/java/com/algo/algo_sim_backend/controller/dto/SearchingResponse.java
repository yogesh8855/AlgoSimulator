package com.algo.algo_sim_backend.controller.dto;

import java.util.List;

public class SearchingResponse {

    /** Original array we searched in */
    private int[] originalArray;

    /** Target value */
    private int target;

    /** Whether the target was found */
    private boolean found;

    /** Index at which target was found, or -1 if not found */
    private int foundIndex;

    /** All visualization steps */
    private List<SearchingStep> steps;

    /** Pseudocode lines to show in the UI */
    private List<String> pseudocodeLines;

    public SearchingResponse() {
    }

    public int[] getOriginalArray() {
        return originalArray;
    }

    public void setOriginalArray(int[] originalArray) {
        this.originalArray = originalArray;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public int getFoundIndex() {
        return foundIndex;
    }

    public void setFoundIndex(int foundIndex) {
        this.foundIndex = foundIndex;
    }

    public List<SearchingStep> getSteps() {
        return steps;
    }

    public void setSteps(List<SearchingStep> steps) {
        this.steps = steps;
    }

    public List<String> getPseudocodeLines() {
        return pseudocodeLines;
    }

    public void setPseudocodeLines(List<String> pseudocodeLines) {
        this.pseudocodeLines = pseudocodeLines;
    }
}

package com.algo.algo_sim_backend.controller.dto;

public class SearchingStep {

    /** Snapshot of the array at this step */
    private int[] arraySnapshot;

    /** Current index being checked (for both linear and binary) */
    private int currentIndex;

    /** Low pointer (for binary search). For linear search, usually 0. */
    private int low;

    /** High pointer (for binary search). For linear search, usually array.length - 1. */
    private int high;

    /** Was the target found at this step? */
    private boolean found;

    /** Human-readable description of what is happening in this step */
    private String description;

    public SearchingStep() {
    }

    public SearchingStep(int[] arraySnapshot,
                         int currentIndex,
                         int low,
                         int high,
                         boolean found,
                         String description) {
        this.arraySnapshot = arraySnapshot;
        this.currentIndex = currentIndex;
        this.low = low;
        this.high = high;
        this.found = found;
        this.description = description;
    }

    public int[] getArraySnapshot() {
        return arraySnapshot;
    }

    public void setArraySnapshot(int[] arraySnapshot) {
        this.arraySnapshot = arraySnapshot;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

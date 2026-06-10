package com.algo.algo_sim_backend.controller.dto;

import java.util.List;

public class SortingResponse {

    private List<SortingStep> steps;
    private List<Integer> sorted;
    private long comparisons;
    private long swaps;

    public SortingResponse() {
    }

    public SortingResponse(
            List<SortingStep> steps,
            List<Integer> sorted,
            long comparisons,
            long swaps
    ) {
        this.steps = steps;
        this.sorted = sorted;
        this.comparisons = comparisons;
        this.swaps = swaps;
    }

    public List<SortingStep> getSteps() {
        return steps;
    }

    public void setSteps(List<SortingStep> steps) {
        this.steps = steps;
    }

    public List<Integer> getSorted() {
        return sorted;
    }

    public void setSorted(List<Integer> sorted) {
        this.sorted = sorted;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void setComparisons(long comparisons) {
        this.comparisons = comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public void setSwaps(long swaps) {
        this.swaps = swaps;
    }
}
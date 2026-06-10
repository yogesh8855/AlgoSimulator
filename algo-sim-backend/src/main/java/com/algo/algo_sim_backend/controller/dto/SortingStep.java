package com.algo.algo_sim_backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SortingStep {
    public String action;          // "compare","swap","snapshot","selectPivot","shift","insert","markSorted"
    public List<Integer> indices;  // indices involved
    public List<Integer> snapshot; // full array snapshot after action (optional)
    public String note;            // human readable narration
    public String pseudocodeLineId; // optional pseudocode line id to highlight

    public SortingStep() {}

    public SortingStep(String action, List<Integer> indices, List<Integer> snapshot, String note, String pseudocodeLineId) {
        this.action = action;
        this.indices = indices;
        this.snapshot = snapshot;
        this.note = note;
        this.pseudocodeLineId = pseudocodeLineId;
    }

    public SortingStep(String action, List<Integer> indices, List<Integer> snapshot, String note) {
        this(action, indices, snapshot, note, null);
    }

    public SortingStep(String action, List<Integer> indices, String note) {
        this(action, indices, null, note, null);
    }
}

package com.algo.algo_sim_backend.controller;

import com.algo.algo_sim_backend.controller.dto.SortingStep;
import com.algo.algo_sim_backend.controller.dto.SortingRequest;
import com.algo.algo_sim_backend.controller.dto.SortingResponse;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/sorting")
@CrossOrigin(origins = "*")
public class SortingController {

    @PostMapping("/visualize")
    public SortingResponse visualize(@RequestBody SortingRequest req) {
        if (req == null || req.getArray() == null) throw new IllegalArgumentException("array required");
        String alg = req.getAlgorithm() == null ? "bubble" : req.getAlgorithm().trim().toLowerCase();
        List<Integer> arr = new ArrayList<>(req.getArray());
        List<SortingStep> steps = new ArrayList<>();
        // initial snapshot
        steps.add(new SortingStep("snapshot", null, new ArrayList<>(arr), "Initial array"));

        SortTracker tracker = new SortTracker(arr, steps);

        switch (alg) {
            case "bubble": tracker.bubbleSort(); break;
            case "selection": tracker.selectionSort(); break;
            case "insertion": tracker.insertionSort(); break;
            case "quick": tracker.quickSort(); break;
            case "merge": tracker.mergeSort(); break;
            default: tracker.bubbleSort();
        }

        // mark all indices as sorted at the end (for clear UI)
        for (int i = 0; i < arr.size(); i++) {
            steps.add(new SortingStep("markSorted", Arrays.asList(i), new ArrayList<>(arr), "Element at index " + i + " is in final position"));
        }

        return new SortingResponse(steps, arr, tracker.comparisons, tracker.swaps);
    }

    // --- Helper: internal tracker to produce descriptive steps and counts ---
    private static class SortTracker {
        List<Integer> a;
        List<SortingStep> steps;
        long comparisons = 0;
        long swaps = 0;

        SortTracker(List<Integer> a, List<SortingStep> steps) { this.a = a; this.steps = steps; }

        void recordCompare(int i, int j) {
            comparisons++;
            steps.add(new SortingStep("compare", Arrays.asList(i, j), new ArrayList<>(a),
                    "Comparing index " + i + " (val=" + a.get(i) + ") and index " + j + " (val=" + a.get(j) + ")"));
        }

        void recordSwap(int i, int j) {
            swaps++;
            Collections.swap(a, i, j);
            steps.add(new SortingStep("swap", Arrays.asList(i, j), new ArrayList<>(a),
                    "Swapped indices " + i + " and " + j + " -> array updated"));
        }

        void bubbleSort() {
            int n = a.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    recordCompare(j, j + 1);
                    if (a.get(j) > a.get(j + 1)) {
                        recordSwap(j, j + 1);
                    } else {
                        // snapshot showing no-swap decision
                        steps.add(new SortingStep("snapshot", null, new ArrayList<>(a),
                                "No swap needed for indices " + j + " and " + (j + 1)));
                    }
                }
                steps.add(new SortingStep("markSorted", Arrays.asList(n - i - 1), new ArrayList<>(a),
                        "Element at index " + (n - i - 1) + " is now in final position"));
            }
        }

        void selectionSort() {
            int n = a.size();
            for (int i = 0; i < n - 1; i++) {
                int min = i;
                for (int j = i + 1; j < n; j++) {
                    recordCompare(min, j);
                    if (a.get(j) < a.get(min)) min = j;
                }
                if (min != i) recordSwap(i, min);
                else steps.add(new SortingStep("snapshot", null, new ArrayList<>(a), "No swap needed; min is at index " + i));
                steps.add(new SortingStep("markSorted", Arrays.asList(i), new ArrayList<>(a), "Element at index " + i + " is in final position"));
            }
        }

        void insertionSort() {
            int n = a.size();
            for (int i = 1; i < n; i++) {
                int key = a.get(i);
                int j = i - 1;
                steps.add(new SortingStep("selectKey", Arrays.asList(i), new ArrayList<>(a), "Selected key at index " + i + " (val=" + key + ")"));
                while (j >= 0) {
                    recordCompare(j, j + 1);
                    if (a.get(j) > key) {
                        a.set(j + 1, a.get(j));
                        swaps++; // count shifts as swaps for teaching purposes
                        steps.add(new SortingStep("shift", Arrays.asList(j, j + 1), new ArrayList<>(a),
                                "Shifted value at " + j + " to position " + (j + 1)));
                        j--;
                    } else break;
                }
                a.set(j + 1, key);
                steps.add(new SortingStep("insert", Arrays.asList(j + 1), new ArrayList<>(a),
                        "Inserted key at index " + (j + 1)));
            }
        }

        // Quick & Merge: produce descriptive snapshots during main actions
        void quickSort() {
            quick(0, a.size() - 1);
        }
        void quick(int l, int r) {
            if (l >= r) return;
            int pivot = a.get(r);
            steps.add(new SortingStep("selectPivot", Arrays.asList(r), new ArrayList<>(a), "Selected pivot at index " + r + " (val=" + pivot + ")"));
            int i = l;
            for (int j = l; j < r; j++) {
                recordCompare(j, r);
                if (a.get(j) < pivot) {
                    recordSwap(i, j);
                    i++;
                }
            }
            recordSwap(i, r);
            quick(l, i - 1);
            quick(i + 1, r);
        }

        void mergeSort() {
            merge(0, a.size() - 1);
        }
        void merge(int l, int r) {
            if (l >= r) return;
            int m = (l + r) / 2;
            merge(l, m);
            merge(m + 1, r);
            // perform merge with snapshots
            List<Integer> tmp = new ArrayList<>();
            int i = l, j = m + 1;
            while (i <= m && j <= r) {
                recordCompare(i, j);
                if (a.get(i) <= a.get(j)) tmp.add(a.get(i++));
                else tmp.add(a.get(j++));
            }
            while (i <= m) tmp.add(a.get(i++));
            while (j <= r) tmp.add(a.get(j++));
            for (int k = 0; k < tmp.size(); k++) a.set(l + k, tmp.get(k));
            steps.add(new SortingStep("snapshot", null, new ArrayList<>(a), "Merged segment [" + l + "," + r + "]"));
        }
    }
}

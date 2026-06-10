package com.algo.algo_sim_backend.controller;

import com.algo.algo_sim_backend.controller.dto.SearchingRequest;
import com.algo.algo_sim_backend.controller.dto.SearchingResponse;
import com.algo.algo_sim_backend.controller.dto.SearchingStep;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/searching")
@CrossOrigin(origins = "*")
public class SearchingController {

    @PostMapping("/run")
    public SearchingResponse runSearching(@RequestBody SearchingRequest request) {

        int[] array = request.getArray();
        int target = request.getTarget();
        String algorithm = request.getAlgorithm() == null
                ? "LINEAR"
                : request.getAlgorithm().trim().toUpperCase();

        List<SearchingStep> steps = new ArrayList<>();
        boolean found = false;
        int foundIndex = -1;

        if ("BINARY".equals(algorithm)) {
            // we assume array is already sorted for binary search
            int low = 0;
            int high = array.length - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                int[] snap = Arrays.copyOf(array, array.length);
                boolean stepFound = snap[mid] == target;
                String desc = "Check mid index " + mid + " (value " + snap[mid] + ")";

                steps.add(new SearchingStep(snap, mid, low, high, stepFound, desc));

                if (stepFound) {
                    found = true;
                    foundIndex = mid;
                    break;
                } else if (snap[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        } else {
            // default: LINEAR
            for (int i = 0; i < array.length; i++) {
                int[] snap = Arrays.copyOf(array, array.length);
                boolean stepFound = snap[i] == target;
                String desc = "Check index " + i + " (value " + snap[i] + ")";

                steps.add(new SearchingStep(snap, i, 0, array.length - 1, stepFound, desc));

                if (stepFound) {
                    found = true;
                    foundIndex = i;
                    break;
                }
            }
        }

        SearchingResponse response = new SearchingResponse();
        response.setOriginalArray(array);
        response.setTarget(target);
        response.setFound(found);
        response.setFoundIndex(foundIndex);
        response.setSteps(steps);
        response.setPseudocodeLines(getPseudocodeLines(algorithm));

        return response;
    }

    private List<String> getPseudocodeLines(String algorithm) {
        List<String> lines = new ArrayList<>();
        if ("BINARY".equals(algorithm)) {
            lines.add("binarySearch(A, target):");
            lines.add("  low = 0, high = n - 1");
            lines.add("  while low <= high:");
            lines.add("    mid = (low + high) / 2");
            lines.add("    if A[mid] == target: return mid");
            lines.add("    else if A[mid] < target: low = mid + 1");
            lines.add("    else: high = mid - 1");
            lines.add("  return -1");
        } else {
            lines.add("linearSearch(A, target):");
            lines.add("  for i from 0 to n - 1:");
            lines.add("    if A[i] == target:");
            lines.add("      return i");
            lines.add("  return -1");
        }
        return lines;
    }
}

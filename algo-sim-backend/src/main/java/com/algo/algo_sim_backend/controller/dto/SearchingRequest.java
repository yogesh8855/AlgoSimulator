package com.algo.algo_sim_backend.controller.dto;

public class SearchingRequest {

    /** "LINEAR" or "BINARY" */
    private String algorithm;

    /** Input array to search in */
    private int[] array;

    /** Target value to search for */
    private int target;

    public SearchingRequest() {
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}

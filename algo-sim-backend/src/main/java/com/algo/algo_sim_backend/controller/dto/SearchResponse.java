package com.algo.algo_sim_backend.controller.dto;

import java.util.List;

public class SearchResponse {
    public List<SearchStep> steps;    // chronological trace for visualization
    public List<int[]> path;         // if found, list of {r,c} from start->goal (inclusive)
    public boolean found;
    public int exploredCount;

    // New counters / metadata
    public int nodesOpened;
    public int nodesClosed;
    public int frontierPeakSize;
}

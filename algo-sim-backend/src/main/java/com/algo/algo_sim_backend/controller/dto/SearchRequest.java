package com.algo.algo_sim_backend.controller.dto;

import java.util.List;
import java.util.Map;

public class SearchRequest {
    public String algorithm; // "bfs", "dfs", "astar"
    public int rows;
    public int cols;
    // grid: list of blocked coordinates
    public List<int[]> blockedCells; // each int[]{r,c}
    public int[] start; // {r,c}
    public int[] goal;  // {r,c}
    public Map<String,Object> options; // e.g. {"heuristic":"manhattan", "allowDiagonal": true}
    public String heuristic; // optional top-level heuristic for convenience
}

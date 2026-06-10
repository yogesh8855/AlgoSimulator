package com.algo.algo_sim_backend.controller;

import com.algo.algo_sim_backend.controller.dto.SearchRequest;
import com.algo.algo_sim_backend.controller.dto.SearchResponse;
import com.algo.algo_sim_backend.service.search.AStar;
import com.algo.algo_sim_backend.service.search.BFS;
import com.algo.algo_sim_backend.service.search.DFS;
import com.algo.algo_sim_backend.service.search.SearchAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class AISearchController {

    @PostMapping("/run")
    public ResponseEntity<SearchResponse> runSearch(@RequestBody SearchRequest req) {
        String alg = req.algorithm == null ? "bfs" : req.algorithm.trim().toLowerCase();
        SearchAlgorithm algorithm;
        switch (alg) {
            case "dfs": algorithm = new DFS(); break;
            case "astar": algorithm = new AStar(); break;
            default: algorithm = new BFS(); break;
        }
        SearchResponse res = algorithm.searchGrid(req);
        return ResponseEntity.ok(res);
    }
}

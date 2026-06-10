package com.algo.algo_sim_backend.service.search;

import com.algo.algo_sim_backend.controller.dto.SearchRequest;
import com.algo.algo_sim_backend.controller.dto.SearchResponse;

public interface SearchAlgorithm {
    SearchResponse searchGrid(SearchRequest req);
}

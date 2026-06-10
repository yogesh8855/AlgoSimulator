package com.algo.algo_sim_backend.service.search;

import com.algo.algo_sim_backend.controller.dto.SearchRequest;
import com.algo.algo_sim_backend.controller.dto.SearchResponse;
import com.algo.algo_sim_backend.controller.dto.SearchStep;

import java.util.*;

/**
 * A* search on a grid with choice of heuristics. Produces chronological SearchStep objects,
 * includes nodeId, parent pointers, g/h/f and optional pq snapshots for visualization.
 */
public class AStar implements SearchAlgorithm {

    private static final int[][] DIRS_4 = {{-1,0},{1,0},{0,-1},{0,1}};
    private static final int[][] DIRS_8 = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};

    private interface Heuristic {
        int h(int r1,int c1,int r2,int c2);
    }

    private Heuristic selectHeuristic(String name) {
        if (name == null) return (r1,c1,r2,c2) -> Math.abs(r1 - r2) + Math.abs(c1 - c2); // default manhattan
        switch (name.trim().toLowerCase()) {
            case "euclidean": return (r1,c1,r2,c2) -> (int)Math.round(Math.hypot(r1 - r2, c1 - c2));
            case "chebyshev": return (r1,c1,r2,c2) -> Math.max(Math.abs(r1-r2), Math.abs(c1-c2));
            default: return (r1,c1,r2,c2) -> Math.abs(r1 - r2) + Math.abs(c1 - c2);
        }
    }

    private int idOf(int r,int c,int cols) { return r * cols + c; }

    @Override
    public SearchResponse searchGrid(SearchRequest req) {
        int R = req.rows;
        int C = req.cols;
        if (R <= 0 || C <= 0) throw new IllegalArgumentException("rows/cols must be positive");

        boolean[][] blocked = new boolean[R][C];
        if (req.blockedCells != null) {
            for (int[] b : req.blockedCells) {
                if (b == null || b.length < 2) continue;
                int br = b[0], bc = b[1];
                if (br >= 0 && br < R && bc >= 0 && bc < C) blocked[br][bc] = true;
            }
        }

        if (req.start == null || req.goal == null || req.start.length < 2 || req.goal.length < 2)
            throw new IllegalArgumentException("start and goal must be provided as [row,col]");

        int sr = req.start[0], sc = req.start[1];
        int gr = req.goal[0], gc = req.goal[1];

        if (sr < 0 || sr >= R || sc < 0 || sc >= C) throw new IllegalArgumentException("start out of bounds");
        if (gr < 0 || gr >= R || gc < 0 || gc >= C) throw new IllegalArgumentException("goal out of bounds");
        if (blocked[sr][sc]) throw new IllegalArgumentException("start is blocked");
        if (blocked[gr][gc]) throw new IllegalArgumentException("goal is blocked");

        // choose heuristic and neighbors
        String heuristicName = req.heuristic != null ? req.heuristic : (req.options != null ? (String)req.options.get("heuristic") : null);
        Heuristic heuristic = selectHeuristic(heuristicName);
        boolean allowDiagonal = req.options != null && Boolean.TRUE.equals(req.options.get("allowDiagonal"));
        int[][] DIRS = allowDiagonal ? DIRS_8 : DIRS_4;

        List<SearchStep> steps = new ArrayList<>();
        int[][] parentR = new int[R][C], parentC = new int[R][C];
        for (int i = 0; i < R; i++) Arrays.fill(parentR[i], -1);
        int[][] gscore = new int[R][C];
        for (int i = 0; i < R; i++) Arrays.fill(gscore[i], Integer.MAX_VALUE);

        // open set tied to f value: entries are {f, g, r, c}
        Comparator<int[]> cmp = Comparator.comparingInt(a -> a[0]);
        PriorityQueue<int[]> open = new PriorityQueue<>(cmp);

        boolean[][] closed = new boolean[R][C];

        gscore[sr][sc] = 0;
        int f0 = heuristic.h(sr, sc, gr, gc);
        open.add(new int[]{f0, 0, sr, sc});
        steps.add(buildStep("open", sr, sc, 0, heuristic.h(sr,sc,gr,gc), f0, "start", R, C, open));

        boolean found = false;
        int explored = 0;
        int nodesOpened = 1;
        int nodesClosed = 0;
        int frontierPeak = Math.max(open.size(), 1);

        while (!open.isEmpty()) {
            int[] cur = open.poll();
            int fcur = cur[0], gcur = cur[1], r = cur[2], c = cur[3];
            // skip stale entries if already closed
            if (closed[r][c]) continue;

            closed[r][c] = true;
            nodesClosed++;
            nodesOpened = Math.max(nodesOpened, nodesClosed + open.size());
            steps.add(buildStep("close", r, c, gscore[r][c], heuristic.h(r,c,gr,gc), gscore[r][c] + heuristic.h(r,c,gr,gc),
                    "closing node", R, C, open));
            explored++;

            if (r == gr && c == gc) {
                found = true;
                break;
            }

            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if (blocked[nr][nc] || closed[nr][nc]) continue;
                int cost = 1;
                // diagonal may have sqrt(2) cost approximated as 1 or 1 (for grid simplicity); keep unit cost
                int tentative = gscore[r][c] + cost;
                if (tentative < gscore[nr][nc]) {
                    parentR[nr][nc] = r;
                    parentC[nr][nc] = c;
                    gscore[nr][nc] = tentative;
                    int h = heuristic.h(nr, nc, gr, gc);
                    int f = tentative + h;
                    open.add(new int[]{f, tentative, nr, nc});
                    steps.add(buildStep("open", nr, nc, tentative, h, f,
                            "discovered from (" + r + "," + c + ")", R, C, open));
                } else {
                    // optionally emit relax attempt
                    steps.add(buildStep("peek", nr, nc, gscore[nr][nc] == Integer.MAX_VALUE ? null : gscore[nr][nc],
                            heuristic.h(nr,nc,gr,gc),
                            gscore[nr][nc] == Integer.MAX_VALUE ? null : gscore[nr][nc] + heuristic.h(nr,nc,gr,gc),
                            "no improvement", R, C, open));
                }
            }
            frontierPeak = Math.max(frontierPeak, open.size());
        }

        List<int[]> path = new ArrayList<>();
        if (found) {
            int r = gr, c = gc;
            while (!(r == sr && c == sc)) {
                path.add(new int[]{r, c});
                int pr = parentR[r][c], pc = parentC[r][c];
                r = pr; c = pc;
            }
            path.add(new int[]{sr, sc});
            Collections.reverse(path);
            for (int[] p : path) steps.add(buildStep("path", p[0], p[1], null, null, null, "path", R, C, open));
        }

        SearchResponse resp = new SearchResponse();
        resp.steps = steps;
        resp.path = path;
        resp.found = found;
        resp.exploredCount = explored;
        resp.nodesOpened = nodesOpened;
        resp.nodesClosed = nodesClosed;
        resp.frontierPeakSize = frontierPeak;
        return resp;
    }

    // helper to create SearchStep and also attach nodeId/parent and PQ snapshot
    private SearchStep buildStep(String type, int r, int c, Integer g, Integer h, Integer f, String note, int R, int C, PriorityQueue<int[]> open) {
        SearchStep s = new SearchStep();
        s.setType(type);
        s.setRow(r);
        s.setCol(c);
        s.setG(g);
        s.setH(h);
        s.setF(f);
        s.setNote(note);
        s.setNodeId(idOf(r,c,C));
        // parent is not set here by buildStep caller; frontend can display if SearchResponse included parents - AStar stores parents in arrays
        // attach PQ snapshot for visualization (list of [r,c,f])
        if (open != null) {
            List<int[]> snap = new ArrayList<>();
            for (int[] e : open) {
                snap.add(new int[]{e[2], e[3], e[0]});
            }
            s.setPqSnapshot(snap);
        }
        return s;
    }
}

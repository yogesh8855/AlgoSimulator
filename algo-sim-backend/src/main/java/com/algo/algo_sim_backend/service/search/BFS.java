package com.algo.algo_sim_backend.service.search;

import com.algo.algo_sim_backend.controller.dto.*;
import java.util.*;

public class BFS implements SearchAlgorithm {

    private final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};

    @Override
    public SearchResponse searchGrid(SearchRequest req) {
        int R = req.rows, C = req.cols;
        boolean[][] blocked = new boolean[R][C];
        if (req.blockedCells != null) {
            for (int[] b : req.blockedCells) {
                if (b[0] >= 0 && b[0] < R && b[1] >=0 && b[1] < C) blocked[b[0]][b[1]] = true;
            }
        }
        int sr = req.start[0], sc = req.start[1];
        int gr = req.goal[0], gc = req.goal[1];

        List<SearchStep> steps = new ArrayList<>();
        boolean[][] visited = new boolean[R][C];
        int[][] parentR = new int[R][C], parentC = new int[R][C];
        for (int i=0;i<R;i++) Arrays.fill(parentR[i], -1);
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr,sc});
        visited[sr][sc] = true;
        steps.add(buildStep("open", sr, sc, null, null, null, "start", R, C, q));

        boolean found = false;
        int explored = 0;
        int nodesOpened = 1, nodesClosed = 0, frontierPeak = 1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            nodesClosed++;
            steps.add(buildStep("close", r, c, null, null, null, "closing node", R, C, q));
            explored++;

            if (r==gr && c==gc) { found = true; break; }

            for (int[] d : DIRS) {
                int nr=r+d[0], nc=c+d[1];
                if (nr<0||nr>=R||nc<0||nc>=C) continue;
                if (blocked[nr][nc] || visited[nr][nc]) continue;
                visited[nr][nc] = true;
                parentR[nr][nc] = r;
                parentC[nr][nc] = c;
                steps.add(buildStep("open", nr, nc, null, null, null, "discovered", R, C, q));
                q.add(new int[]{nr,nc});
                nodesOpened++;
                frontierPeak = Math.max(frontierPeak, q.size());
            }
        }

        List<int[]> path = new ArrayList<>();
        if (found) {
            int r=gr, c=gc;
            while (!(r==sr && c==sc)) {
                path.add(new int[]{r,c});
                int pr = parentR[r][c], pc = parentC[r][c];
                r = pr; c = pc;
            }
            path.add(new int[]{sr,sc});
            Collections.reverse(path);
            for (int[] p : path) steps.add(buildStep("path", p[0], p[1], null, null, null, "path", R, C, q));
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

    // helper that builds a SearchStep and attaches pq snapshot from queue
    private SearchStep buildStep(String type, int r, int c, Integer g, Integer h, Integer f, String note, int R, int C, Queue<int[]> q) {
        SearchStep s = new SearchStep(type, r, c, g, h, f, note);
        s.setNodeId(r * C + c);
        // parent not included here; BFS code keeps parent arrays but we don't attach them to steps (could be added)
        if (q != null) {
            List<int[]> snap = new ArrayList<>();
            for (int[] e: q) snap.add(new int[]{e[0], e[1], -1});
            s.setPqSnapshot(snap);
        }
        return s;
    }
}

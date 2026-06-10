package com.algo.algo_sim_backend.service.search;

import com.algo.algo_sim_backend.controller.dto.*;
import java.util.*;

public class DFS implements SearchAlgorithm {
    private final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};

    @Override
    public SearchResponse searchGrid(SearchRequest req) {
        int R=req.rows, C=req.cols;
        boolean[][] blocked = new boolean[R][C];
        if (req.blockedCells!=null) for (int[] b: req.blockedCells) if (b[0]>=0&&b[0]<R&&b[1]>=0&&b[1]<C) blocked[b[0]][b[1]]=true;
        int[] s=req.start, g=req.goal;
        List<SearchStep> steps=new ArrayList<>();
        boolean[][] visited=new boolean[R][C];
        int[][] pr=new int[R][C], pc=new int[R][C]; for(int i=0;i<R;i++) Arrays.fill(pr[i], -1);

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{s[0], s[1]});
        visited[s[0]][s[1]] = true;
        steps.add(buildStep("open", s[0], s[1], null, null, null, "start", R, C, stack));

        boolean found=false; int explored=0;
        int nodesOpened=1, nodesClosed=0, frontierPeak=1;

        while(!stack.isEmpty()) {
            int[] cur = stack.pop();
            int r=cur[0], c=cur[1];
            nodesClosed++;
            steps.add(buildStep("close", r, c, null, null, null, "closing", R, C, stack));
            explored++;
            if (r==g[0] && c==g[1]) { found=true; break; }

            for (int i=0;i<DIRS.length;i++) {
                int[] d = DIRS[i];
                int nr=r+d[0], nc=c+d[1];
                if (nr<0||nr>=R||nc<0||nc>=C) continue;
                if (blocked[nr][nc] || visited[nr][nc]) continue;
                visited[nr][nc]=true;
                pr[nr][nc]=r; pc[nr][nc]=c;
                steps.add(buildStep("open", nr, nc, null, null, null, "push to stack", R, C, stack));
                stack.push(new int[]{nr,nc});
                nodesOpened++;
                frontierPeak = Math.max(frontierPeak, stack.size());
            }
        }

        List<int[]> path=new ArrayList<>();
        if (found) {
            int rr=g[0], cc=g[1];
            while (!(rr==s[0] && cc==s[1])) {
                path.add(new int[]{rr,cc});
                int tr=pr[rr][cc], tc=pc[rr][cc];
                rr=tr; cc=tc;
            }
            path.add(s); Collections.reverse(path);
            for (int[] p: path) steps.add(buildStep("path", p[0], p[1], null, null, null, "path", R, C, stack));
        }
        SearchResponse resp=new SearchResponse();
        resp.steps=steps; resp.path=path; resp.found=found; resp.exploredCount=explored;
        resp.nodesOpened = nodesOpened; resp.nodesClosed = nodesClosed; resp.frontierPeakSize = frontierPeak;
        return resp;
    }

    private SearchStep buildStep(String type, int r, int c, Integer g, Integer h, Integer f, String note, int R, int C, Deque<int[]> stack) {
        SearchStep s = new SearchStep(type, r, c, g, h, f, note);
        s.setNodeId(r * C + c);
        if (stack != null) {
            List<int[]> snap = new ArrayList<>();
            for (int[] e: stack) snap.add(new int[]{e[0], e[1], -1});
            s.setPqSnapshot(snap);
        }
        return s;
    }
}

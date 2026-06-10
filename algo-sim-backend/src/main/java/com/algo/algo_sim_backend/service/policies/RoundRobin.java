package com.algo.algo_sim_backend.service.policies;

import com.algo.algo_sim_backend.model.Job;

import java.util.*;

public class RoundRobin {

    public static class GanttEntry {
        public final String id;
        public final int start;
        public final int end;
        public GanttEntry(String id, int start, int end) { this.id = id; this.start = start; this.end = end; }
    }

    public static class Result {
        public final List<Job> completed;   // jobs with computed metrics (completion etc.)
        public final List<GanttEntry> gantt; // execution slices in chronological order
        public Result(List<Job> completed, List<GanttEntry> gantt) { this.completed = completed; this.gantt = gantt; }
    }

    public Result run(List<Job> jobs, int quantum) {
        if (quantum <= 0) quantum = 1;

        // Defensive copy and sort by arrival (stable ordering)
        List<Job> input = new ArrayList<>(jobs);
        input.sort(Comparator.comparingInt(Job::getArrivalTime)
                .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""));

        // remaining bursts map by processId
        Map<String, Integer> remaining = new HashMap<>();
        for (Job j : input) {
            remaining.put(j.getProcessId(), j.getBurstTime());
            // reset outputs (defensive)
            j.setCompletionTime(0);
            j.setTurnaroundTime(0);
            j.setWaitingTime(0);
        }

        Queue<Job> ready = new ArrayDeque<>();
        List<Job> completed = new ArrayList<>();
        List<GanttEntry> gantt = new ArrayList<>();

        int time = 0;
        int idx = 0; // index into input for arrivals

        // enqueue arrivals at time 0
        while (idx < input.size() && input.get(idx).getArrivalTime() <= time) {
            ready.add(input.get(idx));
            idx++;
        }

        while (!ready.isEmpty() || idx < input.size()) {
            if (ready.isEmpty()) {
                // jump to next arrival
                Job nextArr = input.get(idx);
                time = Math.max(time, nextArr.getArrivalTime());
                while (idx < input.size() && input.get(idx).getArrivalTime() <= time) {
                    ready.add(input.get(idx));
                    idx++;
                }
                continue;
            }

            Job cur = ready.poll();
            String pid = cur.getProcessId();
            int rem = remaining.getOrDefault(pid, cur.getBurstTime());
            int exec = Math.min(rem, quantum);

            int start = time;
            int end = time + exec;

            // Record Gantt slice
            gantt.add(new GanttEntry(pid, start, end));

            // Advance time and enqueue arrivals that arrived during this slice
            time = end;
            while (idx < input.size() && input.get(idx).getArrivalTime() <= time) {
                ready.add(input.get(idx));
                idx++;
            }

            rem -= exec;
            remaining.put(pid, rem);

            if (rem > 0) {
                // not finished -> re-enqueue at tail
                ready.add(cur);
            } else {
                // finished -> set completion/turnaround/waiting
                cur.setCompletionTime(time);
                cur.setTurnaroundTime(cur.getCompletionTime() - cur.getArrivalTime());
                cur.setWaitingTime(cur.getTurnaroundTime() - cur.getBurstTime());
                completed.add(cur);
            }
        }

        return new Result(completed, gantt);
    }
}

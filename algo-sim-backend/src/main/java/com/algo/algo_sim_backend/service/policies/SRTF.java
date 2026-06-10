package com.algo.algo_sim_backend.service.policies;

import com.algo.algo_sim_backend.model.Job;

import java.util.*;

public class SRTF {

    public List<Job> schedule(List<Job> jobs) {
        // Defensive copy
        List<Job> input = new ArrayList<>(jobs);
        input.sort(Comparator.comparingInt(Job::getArrivalTime).thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""));

        int n = input.size();
        Map<String, Integer> remaining = new HashMap<>();
        for (Job j : input) {
            remaining.put(j.getProcessId(), j.getBurstTime());
            j.setCompletionTime(0);
            j.setTurnaroundTime(0);
            j.setWaitingTime(0);
        }

        List<Job> completed = new ArrayList<>();
        int time = 0;
        int idx = 0; // next arrival index
        // ready list - will select the one with smallest remaining
        List<Job> ready = new ArrayList<>();

        while (completed.size() < n) {
            // add arrivals at current time
            while (idx < input.size() && input.get(idx).getArrivalTime() <= time) {
                ready.add(input.get(idx));
                idx++;
            }

            if (ready.isEmpty()) {
                if (idx < input.size()) {
                    // fast-forward
                    time = Math.max(time, input.get(idx).getArrivalTime());
                    continue;
                } else break;
            }

            // choose job with smallest remaining burst (tie by arrival then pid)
            Job current = Collections.min(ready, Comparator
                    .comparingInt((Job j) -> remaining.get(j.getProcessId()))
                    .thenComparingInt(Job::getArrivalTime)
                    .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""));

            // execute one time unit
            remaining.put(current.getProcessId(), remaining.get(current.getProcessId()) - 1);
            time++;

            // if it finished
            if (remaining.get(current.getProcessId()) == 0) {
                current.setCompletionTime(time);
                current.setTurnaroundTime(current.getCompletionTime() - current.getArrivalTime());
                current.setWaitingTime(current.getTurnaroundTime() - current.getBurstTime());
                completed.add(current);
                ready.remove(current);
            }

            // If new arrivals occurred at this time they will be added in next loop iteration
        }

        // Return completed jobs (they contain computed metrics). If any job never finished (shouldn't happen), set defaults.
        return completed;
    }
}

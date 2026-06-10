package com.algo.algo_sim_backend.service.policies;

import com.algo.algo_sim_backend.model.Job;

import java.util.*;

/**
 * Preemptive Priority Scheduling (lower priority number = higher priority).
 * At each time unit choose available job with highest priority (lowest priority number).
 */
public class PriorityPreemptive {

    public List<Job> schedule(List<Job> jobs) {
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
        int idx = 0;
        List<Job> ready = new ArrayList<>();

        while (completed.size() < n) {
            while (idx < input.size() && input.get(idx).getArrivalTime() <= time) {
                ready.add(input.get(idx));
                idx++;
            }

            if (ready.isEmpty()) {
                if (idx < input.size()) {
                    time = Math.max(time, input.get(idx).getArrivalTime());
                    continue;
                } else break;
            }

            // choose job with highest priority (smallest priority value)
            Job current = Collections.min(ready, Comparator
                    .comparingInt(Job::getPriority)
                    .thenComparingInt(Job::getArrivalTime)
                    .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""));

            // execute one time unit
            remaining.put(current.getProcessId(), remaining.get(current.getProcessId()) - 1);
            time++;

            if (remaining.get(current.getProcessId()) == 0) {
                current.setCompletionTime(time);
                current.setTurnaroundTime(current.getCompletionTime() - current.getArrivalTime());
                current.setWaitingTime(current.getTurnaroundTime() - current.getBurstTime());
                completed.add(current);
                ready.remove(current);
            }
        }

        return completed;
    }
}

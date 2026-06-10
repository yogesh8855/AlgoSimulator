package com.algo.algo_sim_backend.service.policies;

import com.algo.algo_sim_backend.model.Job;

import java.util.*;

public class SJF {
    public List<Job> schedule(List<Job> jobs) {
        // Work on a shallow copy to avoid reordering caller list
        List<Job> input = new ArrayList<>(jobs);
        // sort by arrival then by burst then by id for determinism
        input.sort(Comparator
                .comparingInt(Job::getArrivalTime)
                .thenComparingInt(Job::getBurstTime)
                .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""));

        int currentTime = 0;
        List<Job> completed = new ArrayList<>();
        List<Job> remaining = new ArrayList<>(input);

        while (!remaining.isEmpty()) {
            // collect available jobs
            List<Job> available = new ArrayList<>();
            for (Job j : remaining) {
                if (j.getArrivalTime() <= currentTime) available.add(j);
            }

            if (available.isEmpty()) {
                // advance time to next arrival instead of incrementing one by one
                int nextArrival = remaining.stream()
                        .mapToInt(Job::getArrivalTime)
                        .min()
                        .orElse(currentTime);
                currentTime = Math.max(currentTime, nextArrival);
                continue;
            }

            // choose shortest burst; tie-breaker arrival then id
            Job next = available.stream()
                    .min(Comparator.comparingInt(Job::getBurstTime)
                            .thenComparingInt(Job::getArrivalTime)
                            .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""))
                    .get();

            // compute timings
            int start = Math.max(currentTime, next.getArrivalTime());
            currentTime = start + next.getBurstTime();
            next.setCompletionTime(currentTime);
            next.setTurnaroundTime(next.getCompletionTime() - next.getArrivalTime());
            next.setWaitingTime(next.getTurnaroundTime() - next.getBurstTime());

            completed.add(next);
            remaining.remove(next);
        }

        return completed;
    }
}

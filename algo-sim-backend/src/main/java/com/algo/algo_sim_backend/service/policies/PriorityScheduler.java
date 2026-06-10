package com.algo.algo_sim_backend.service.policies;

import com.algo.algo_sim_backend.model.Job;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduler {
    public List<Job> schedule(List<Job> jobs) {
        // Work on copy to avoid mutating caller's list
        List<Job> input = new ArrayList<>(jobs);
        // sort by arrival then priority then id for determinism
        input.sort(Comparator
                .comparingInt(Job::getArrivalTime)
                .thenComparingInt(Job::getPriority)
                .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""));

        int currentTime = 0;
        List<Job> completed = new ArrayList<>();
        List<Job> remaining = new ArrayList<>(input);

        while (!remaining.isEmpty()) {
            List<Job> available = new ArrayList<>();
            for (Job j : remaining) {
                if (j.getArrivalTime() <= currentTime) available.add(j);
            }

            if (available.isEmpty()) {
                // advance to next arrival instead of looping +1
                int nextArrival = remaining.stream()
                        .mapToInt(Job::getArrivalTime)
                        .min()
                        .orElse(currentTime);
                currentTime = Math.max(currentTime, nextArrival);
                continue;
            }

            // lower priority value = higher priority (as you had)
            Job next = available.stream()
                    .min(Comparator.comparingInt(Job::getPriority)
                            .thenComparingInt(Job::getArrivalTime)
                            .thenComparing(j -> j.getProcessId() != null ? j.getProcessId() : ""))
                    .get();

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

package com.algo.algo_sim_backend.service.policies;

import com.algo.algo_sim_backend.model.Job;
import java.util.*;

public class FCFS {
    public List<Job> schedule(List<Job> jobs) {
        jobs.sort(Comparator.comparingInt(Job::getArrivalTime));

        int currentTime = 0;
        for (Job job : jobs) {
            if (currentTime < job.getArrivalTime())
                currentTime = job.getArrivalTime();

            job.setCompletionTime(currentTime + job.getBurstTime());
            job.setTurnaroundTime(job.getCompletionTime() - job.getArrivalTime());
            job.setWaitingTime(job.getTurnaroundTime() - job.getBurstTime());

            currentTime = job.getCompletionTime();
        }
        return jobs;
    }
}

package com.algo.algo_sim_backend.service;

import com.algo.algo_sim_backend.model.Job;
import com.algo.algo_sim_backend.service.policies.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerService {

    public List<Job> scheduleJobs(List<Job> jobs, String algorithm, int timeQuantum) {
        String alg = algorithm == null ? "" : algorithm.trim().toLowerCase();

        if (alg.equals("fcfs") || alg.contains("first")) {
            return new FCFS().schedule(jobs);
        }

        if (alg.equals("sjf") || alg.contains("shortest") && !alg.contains("remaining")) { // non-preemptive SJF
            return new SJF().schedule(jobs);
        }

        if (alg.equals("srtf") || alg.contains("shortest remaining") || alg.contains("remaining")) {
            return new SRTF().schedule(jobs);
        }

        if (alg.equals("priority-preemptive") || alg.contains("priority") && alg.contains("preemptive")) {
            return new PriorityPreemptive().schedule(jobs);
        }

        if (alg.equals("priority") || alg.contains("priority") && !alg.contains("preemptive")) {
            return new PriorityScheduler().schedule(jobs); // non-preemptive existing
        }

        if (alg.equals("rr") || alg.equals("round robin") || alg.contains("round")) {
            // RoundRobin now returns Result; controller will handle response packaging
            RoundRobin rr = new RoundRobin();
            RoundRobin.Result res = rr.run(jobs, timeQuantum);
            // return completed in completion order (controller will reorder as needed)
            return res.completed;
        }

        throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
    }
}


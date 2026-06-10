package com.algo.algo_sim_backend.controller;
import com.algo.algo_sim_backend.model.Job;
import com.algo.algo_sim_backend.service.SchedulerService;
import com.algo.algo_sim_backend.service.policies.RoundRobin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scheduler")
@CrossOrigin(origins = "*") // Allow JavaFX frontend to connect
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/run")
    public SchedulerResponse runScheduler(@RequestBody SchedulerRequest request) {
        List<Job> resultCompleted = schedulerService.scheduleJobs(
                request.getJobs(),
                request.getAlgorithm(),
                request.getTimeQuantum()
        );

        // Map completed jobs by processId for quick lookup
        Map<String, Job> byId = new HashMap<>();
        for (Job j : resultCompleted) byId.put(j.getProcessId(), j);

        // Build response jobs in the same order as request
        List<Job> ordered = new ArrayList<>();
        for (Job reqJob : request.getJobs()) {
            Job computed = byId.get(reqJob.getProcessId());
            if (computed != null) {
                ordered.add(computed);
            } else {
                // Fallback: if algorithm returned no computed job (shouldn't happen), include original
                ordered.add(reqJob);
            }
        }

        double avgWaiting = ordered.stream().mapToInt(Job::getWaitingTime).average().orElse(0);
        double avgTurnaround = ordered.stream().mapToInt(Job::getTurnaroundTime).average().orElse(0);

        return new SchedulerResponse(ordered, avgWaiting, avgTurnaround);
    }


    // --- Response DTO ---
    public static class SchedulerResponse {
        private List<Job> jobs;
        private double averageWaitingTime;
        private double averageTurnaroundTime;

        public SchedulerResponse(List<Job> jobs, double averageWaitingTime, double averageTurnaroundTime) {
            this.jobs = jobs;
            this.averageWaitingTime = averageWaitingTime;
            this.averageTurnaroundTime = averageTurnaroundTime;
        }

        public List<Job> getJobs() { return jobs; }
        public double getAverageWaitingTime() { return averageWaitingTime; }
        public double getAverageTurnaroundTime() { return averageTurnaroundTime; }
    }


    // DTO for request body
    public static class SchedulerRequest {
        private List<Job> jobs;
        private String algorithm;
        private int timeQuantum;

        public List<Job> getJobs() { return jobs; }
        public void setJobs(List<Job> jobs) { this.jobs = jobs; }

        public String getAlgorithm() { return algorithm; }
        public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

        public int getTimeQuantum() { return timeQuantum; }
        public void setTimeQuantum(int timeQuantum) { this.timeQuantum = timeQuantum; }
    }

    // inside SchedulerController (add this class)
    public static class VisualizeResponse {
        private java.util.List<com.algo.algo_sim_backend.controller.dto.SchedulerStep> steps;
        private java.util.List<com.algo.algo_sim_backend.model.Job> jobs;
        public VisualizeResponse() {}
        public VisualizeResponse(java.util.List<com.algo.algo_sim_backend.controller.dto.SchedulerStep> steps,
                                 java.util.List<com.algo.algo_sim_backend.model.Job> jobs) {
            this.steps = steps; this.jobs = jobs;
        }
        public java.util.List<com.algo.algo_sim_backend.controller.dto.SchedulerStep> getSteps(){ return steps; }
        public java.util.List<com.algo.algo_sim_backend.model.Job> getJobs(){ return jobs; }
    }

}

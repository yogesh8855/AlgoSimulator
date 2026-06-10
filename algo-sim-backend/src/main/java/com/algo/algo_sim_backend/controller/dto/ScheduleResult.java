package com.algo.algo_sim_backend.controller.dto;

import com.algo.algo_sim_backend.model.Job;
import java.util.List;

public class ScheduleResult {

    private List<Job> jobs;
    private List<GanttEntry> gantt;

    public ScheduleResult() {
    }

    public ScheduleResult(List<Job> jobs, List<GanttEntry> gantt) {
        this.jobs = jobs;
        this.gantt = gantt;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<GanttEntry> getGantt() {
        return gantt;
    }

    public void setGantt(List<GanttEntry> gantt) {
        this.gantt = gantt;
    }
}
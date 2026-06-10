package com.algo.algo_sim_backend.controller.dto;

public class SchedulerStep {
    private int time;           // logical time unit
    private String type;        // "start", "execute", "preempt", "finish"
    private String pid;         // process id
    private Integer duration;   // optional (for a slice)
    private String note;        // optional

    public SchedulerStep() {}

    public SchedulerStep(int time, String type, String pid, Integer duration, String note) {
        this.time = time; this.type = type; this.pid = pid; this.duration = duration; this.note = note;
    }
    // getters/setters
    public int getTime(){ return time; } public void setTime(int t){ this.time = t; }
    public String getType(){ return type; } public void setType(String type){ this.type = type; }
    public String getPid(){ return pid; } public void setPid(String pid){ this.pid = pid; }
    public Integer getDuration(){ return duration; } public void setDuration(Integer d){ this.duration = d; }
    public String getNote(){ return note; } public void setNote(String note){ this.note = note; }
}

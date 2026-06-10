package com.algo.algo_sim_backend.model;

public class Job {
    private String processId;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    // Output fields
    private int waitingTime;
    private int turnaroundTime;
    private int completionTime;

    // Getters and Setters
    public String getProcessId() { return processId; }
    public void setProcessId(String processId) { this.processId = processId; }

    public int getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getBurstTime() { return burstTime; }
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }

    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }

    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }
}


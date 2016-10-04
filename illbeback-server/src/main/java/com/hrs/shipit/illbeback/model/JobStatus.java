package com.hrs.shipit.illbeback.model;

import com.hrs.shipit.illbeback.model.jenkins.BuildStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;

public class JobStatus {
    private String shortJobName;
    private String jobName;
    private String serverName;
    private int completion;
    private int estimatedDuration;
    private int duration;
    private boolean isBuilding;
    private long timestamp;
    private String color;

    public JobStatus(Job job, BuildStatus status) {
        this.serverName = job.getServer().getServerUrl();
        this.color = job.getColor();
        this.shortJobName = job.getName();
        this.jobName = job.getUrl();
        this.completion = status.getEstimatedDuration() > 0 ? status.getDuration() / status.getEstimatedDuration() : 0;
        this.estimatedDuration = status.getEstimatedDuration();
        this.duration = status.getDuration();
        this.isBuilding = status.isBuilding();
        this.timestamp = status.getTimestamp();
    }

    @Override
    public String toString() {
        return "JobStatus{" + "shortJobName='" + shortJobName + '\'' + ", jobName='" + jobName + '\'' + ", serverName='" + serverName + '\'' + ", completion=" + completion + ", estimatedDuration=" + estimatedDuration + ", duration=" + duration + ", isBuilding=" + isBuilding + ", timestamp=" + timestamp + ", color='" + color + '\'' + '}';
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getCompletion() {
        return completion;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isBuilding() {
        return isBuilding;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getShortJobName() {
        return shortJobName;
    }

    public void setShortJobName(String shortJobName) {
        this.shortJobName = shortJobName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

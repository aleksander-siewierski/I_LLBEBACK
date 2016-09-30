package com.hrs.shipit.illbeback.model;

public class JobStatus {
    private String shortJobName;
    private String jobName;
    private String serverName;
    private int completion;
    private int estimatedDuration;
    private int duration;
    private boolean isBuilding;
    private long timestamp;


    public JobStatus(String jobName, String shortJobName, String serverName, int completion, int estimatedDuration, int duration, boolean isBuilding, long timestamp) {
        this(jobName, completion, estimatedDuration, duration, isBuilding, timestamp);
        this.serverName = serverName;
        this.setShortJobName(shortJobName);
    }

    public JobStatus(String jobName, int completion, int estimatedDuration, int duration, boolean isBuilding, long timestamp) {
        this.jobName = jobName;
        this.completion = completion;
        this.estimatedDuration = estimatedDuration;
        this.duration = duration;
        this.isBuilding = isBuilding;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "JobStatus{" + "jobName='" + jobName + '\'' + "shortJobName='" + shortJobName + '\'' + "serverName='" + serverName + '\'' + ", completion=" + completion + ", estimatedDuration=" + estimatedDuration + ", duration=" + duration + ", isBuilding=" + isBuilding + ", timestamp=" + timestamp + '}';
    }

    public String getJobName() {
        return jobName;
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

    public void setJobName(String jobName) {
        this.jobName = jobName;
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
}

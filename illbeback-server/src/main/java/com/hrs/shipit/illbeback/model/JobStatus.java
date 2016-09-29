package com.hrs.shipit.illbeback.model;

public class JobStatus {
    private String projectName;
    private int completion;
    private int estimatedDuration;
    private int duration;
    private boolean isBuilding;

    public JobStatus(String projectName, int completion, int estimatedDuration, int duration, boolean isBuilding) {
        this.projectName = projectName;
        this.completion = completion;
        this.estimatedDuration = estimatedDuration;
        this.duration = duration;
        this.isBuilding = isBuilding;
    }

    @Override
    public String toString() {
        return "ProjectStatus{" + "projectName='" + projectName + '\'' + ", completion=" + completion + ", estimatedDuration=" + estimatedDuration + ", duration=" + duration + ", isBuilding=" + isBuilding + '}';
    }

    public String getProjectName() {
        return projectName;
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
}

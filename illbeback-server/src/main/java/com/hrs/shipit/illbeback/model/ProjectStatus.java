package com.hrs.shipit.illbeback.model;

public class ProjectStatus {
    private String projectName;
    private int completion;
    private int estimatedDuration;
    private int duration;
    private boolean isBuilding;

    public ProjectStatus(String projectName, int completion, int estimatedDuration, int duration, boolean isBuilding) {
        this.projectName = projectName;
        this.completion = completion;
        this.estimatedDuration = estimatedDuration;
        this.isBuilding = isBuilding;
    }

    @Override
    public String toString() {
        return "ProjectStatus{" + "projectName='" + projectName + '\'' + ", completion=" + completion + ", estimatedDuration=" + estimatedDuration + ", duration=" + duration + ", isBuilding=" + isBuilding + '}';
    }
}

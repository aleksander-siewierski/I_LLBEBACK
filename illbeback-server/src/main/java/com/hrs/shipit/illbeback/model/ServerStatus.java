package com.hrs.shipit.illbeback.model;

import com.hrs.shipit.illbeback.model.jenkins.Job;

import java.util.List;

public class ServerStatus {
    private List<Job> jobs;

    private String serverUrl;

    public ServerStatus(String serverUrl, List<Job> jobs) {
        this.jobs = jobs;
        jobs.forEach(j -> j.setServer(this));
        this.serverUrl = serverUrl;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}

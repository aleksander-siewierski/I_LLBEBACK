package com.hrs.shipit.illbeback.model.jenkins;

import java.util.List;

public class Server {
    private List<Job> jobs;

    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
        jobs.forEach(job -> job.setServer(this));
    }
}

package com.hrs.shipit.illbeback.model.jenkins;

import java.util.List;

public class Server {
    private List<JobListJob> jobs;

    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public List<JobListJob> getJobs() {
        return jobs;
    }

    public void setGetJobs(List<JobListJob> jobs) {
        this.jobs = jobs;
    }
}

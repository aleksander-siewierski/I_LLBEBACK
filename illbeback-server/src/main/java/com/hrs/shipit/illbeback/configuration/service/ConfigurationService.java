package com.hrs.shipit.illbeback.configuration.service;

import com.hrs.shipit.illbeback.model.jenkins.JobList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigurationService {
    private Map<String, JobList> servers = new HashMap<>();

    private List<String> registeredJobs = new ArrayList<>();

    public Map<String, JobList> getServers() {
        return servers;
    }

    public ConfigurationService addServer(String url, JobList jobList) {
        servers.put(url, jobList);

        return this;
    }

    public ConfigurationService removeServer(String url) {
        servers.remove(url);

        return this;
    }

    public List<String> getRegisteredJobs() {
        return registeredJobs;
    }

    public ConfigurationService registerJob(String jobUrl) {
        registeredJobs.add(jobUrl);

        return this;
    }

    public ConfigurationService removeJob(String jobUrl) {
        registeredJobs.remove(jobUrl);

        return this;
    }
}

package com.hrs.shipit.illbeback.configuration.service;

import com.hrs.shipit.illbeback.model.jenkins.JobList;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigurationService {
    private Map<String, JobList> servers = new HashMap<>();

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
}

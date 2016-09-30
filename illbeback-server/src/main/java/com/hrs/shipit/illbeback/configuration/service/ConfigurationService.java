package com.hrs.shipit.illbeback.configuration.service;

import com.hrs.shipit.illbeback.model.jenkins.Job;
import com.hrs.shipit.illbeback.model.jenkins.Server;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigurationService {

    private Map<String, Server> servers = new HashMap<>();

    private List<Job> registeredJobs = new ArrayList<>();

    public Map<String, Server> getServers() {
        return servers;
    }

    public ConfigurationService addServer(String url, Server server) {
        servers.put(url, server);

        return this;
    }

    public ConfigurationService removeServer(String url) {
        servers.remove(url);

        return this;
    }

    public List<Job> getRegisteredJobs() {
        return registeredJobs;
    }

    public ConfigurationService registerJob(Job job) {
        registeredJobs.add(job);

        return this;
    }

    public ConfigurationService registerJob(String jobUrl) {
        Optional<Job> job = findJobByJobUrl(jobUrl);
        if (job.isPresent()) {
            registerJob(job.get());
        }

        return this;
    }

    public ConfigurationService removeJob(String jobUrl) {
        Optional<Job> job = findJobByJobUrl(jobUrl);
        if (job.isPresent()) {
            registeredJobs.remove(job.get());
        }

        return this;
    }

    public ConfigurationService removeJob(Job job) {
        registeredJobs.remove(job);

        return this;
    }

    public Optional<Job> findJobByJobUrl(String jobUrl) {
        String serverUrl = jobUrl.substring(0, jobUrl.indexOf('/', "http://".length() + 1)) + "/";

        return getServers()
            .get(serverUrl)
            .getJobs()
            .stream()
            .filter(j -> j.getUrl().equals(jobUrl))
            .findFirst();
    }

    public boolean containsJob(Job job) {
       return getRegisteredJobs().contains(job);
    }
}

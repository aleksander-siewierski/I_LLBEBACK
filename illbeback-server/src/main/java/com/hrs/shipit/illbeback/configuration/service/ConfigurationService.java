package com.hrs.shipit.illbeback.configuration.service;

import com.hrs.shipit.illbeback.model.ServerStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigurationService {

    private Map<String, ServerStatus> servers = new HashMap<>();

    private List<Job> registeredJobs = new ArrayList<>();

    public Map<String, ServerStatus> getServers() {
        return servers;
    }

    public ConfigurationService addServer(String url, ServerStatus server) {
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

    public ConfigurationService registerJob(Job jobListJob) {
        registeredJobs.add(jobListJob);

        return this;
    }

    public ConfigurationService registerJob(String jobUrl) {
        Optional<Job> job = findJobByJobUrl(jobUrl);
        if (job.isPresent()) {
            registerJob(job.get());
        }

        return this;
    }

    public ConfigurationService removeJob(Job job) {
        registeredJobs.remove(job);

        return this;
    }

    public Optional<Job> findJobByJobUrl(String jobUrl) {
        String serverUrl = jobUrl.substring(0, jobUrl.indexOf('/', "http://".length() + 1)) + "/";

        return getServers().get(serverUrl).getJobs().stream().filter(j -> j.getUrl().equals(jobUrl)).findFirst();
    }

    public boolean containsJob(Job job) {
        return getRegisteredJobs().contains(job);
    }

    public ConfigurationService updateJob(Job oldJob, Job newJob) {
        if (registeredJobs.contains(oldJob)) {
            oldJob.setLastBuild(newJob.getLastBuild());
            oldJob.setColor(newJob.getColor());
            oldJob.setDisplayName(newJob.getDisplayName());
            oldJob.setName(newJob.getName());
            oldJob.setDescription(newJob.getDescription());
        }

        return this;
    }
}

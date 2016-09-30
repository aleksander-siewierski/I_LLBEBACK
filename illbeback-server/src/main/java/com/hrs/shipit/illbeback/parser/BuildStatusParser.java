package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.model.jenkins.BuildStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;
import com.hrs.shipit.illbeback.ssl_workaround.SSLWorkAround;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildStatusParser {

    public JobStatus parseBuildStatusForJob(Job job) {
        SSLWorkAround.disableSslVerification();

        RestTemplate restTemplate = new RestTemplate();
        BuildStatus status = restTemplate.getForObject(job
            .getUrl() + "/lastBuild/api/json?tree=duration,estimatedDuration,building,timestamp", BuildStatus.class);

        return new JobStatus(job.getUrl(), job.getName(), job.getServer().getServerUrl(), status.getDuration() / status.getEstimatedDuration(), status
            .getEstimatedDuration(), status.getDuration(), status.isBuilding(), status.getTimestamp());
    }
}
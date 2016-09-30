package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.model.jenkins.BuildStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;
import com.hrs.shipit.illbeback.ssl_workaround.SSLWorkAround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildStatusParser {

    private static final String PARAMETERS = "/lastBuild/api/json?tree=duration,estimatedDuration,building,timestamp";
    private static final Logger LOG = LoggerFactory.getLogger(BuildStatusParser.class);
    @Autowired private JobParser jobParser;

    public JobStatus parseBuildStatusForJob(Job job) {
        SSLWorkAround.disableSslVerification();

        RestTemplate restTemplate = new RestTemplate();
        try {
            Job updatedJob = jobParser.parseJobStatus(job.getUrl());
            if (job.getLastBuild() != null) {
                BuildStatus status = restTemplate.getForObject(updatedJob.getUrl() + PARAMETERS, BuildStatus.class);

                return new JobStatus(updatedJob.getUrl(), updatedJob.getName(), job.getServer().getServerUrl(), status
                    .getDuration() / status.getEstimatedDuration(), status.getEstimatedDuration(), status
                    .getDuration(), status.isBuilding(), status.getTimestamp(), updatedJob.getColor());
            } else {
                return new JobStatus(updatedJob.getUrl(), updatedJob.getName(), job.getServer()
                    .getServerUrl(), 0, 0, 0, false, 0, updatedJob.getColor());
            }

        } catch (HttpClientErrorException e) {
            LOG.error("Failed updating", e);

            return new JobStatus("failed", "failed", "failed", 0, 0, 0, false, 0, "failed");
        }
    }
}
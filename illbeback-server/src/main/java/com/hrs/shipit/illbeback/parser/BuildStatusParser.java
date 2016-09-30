package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
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
    @Autowired private ConfigurationService configurationService;

    public JobStatus parseBuildStatusForJob(Job job) {
        SSLWorkAround.disableSslVerification();
        BuildStatus status = BuildStatus.empty();
        RestTemplate restTemplate = new RestTemplate();
        try {
            Job updatedJob = jobParser.parseJobStatus(job.getUrl());
            configurationService.updateJob(job, updatedJob);

            if (job.getLastBuild() != null) {
                status = restTemplate.getForObject(job.getUrl() + PARAMETERS, BuildStatus.class);
            }

        } catch (HttpClientErrorException e) {
            LOG.error("Failed updating", e);
        }

        return new JobStatus(job, status);
    }
}
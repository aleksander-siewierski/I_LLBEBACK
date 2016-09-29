package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.model.jenkins.BuildStatus;
import com.hrs.shipit.illbeback.ssl_workaround.SSLWorkAround;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildStatusParser {

    public JobStatus parseBuildStatusForJob(String jobUrl) {
        SSLWorkAround.disableSslVerification();

        RestTemplate restTemplate = new RestTemplate();
        BuildStatus status = restTemplate
            .getForObject(jobUrl + "/lastBuild/api/json?tree=duration,estimatedDuration,building", BuildStatus.class);

        return new JobStatus(jobUrl, status.getDuration() / status.getEstimatedDuration(), status
            .getEstimatedDuration(), status.getDuration(), status.isBuilding());
    }
}
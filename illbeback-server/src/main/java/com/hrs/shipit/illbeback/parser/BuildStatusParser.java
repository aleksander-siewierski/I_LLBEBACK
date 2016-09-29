package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.ProjectStatus;
import com.hrs.shipit.illbeback.model.jenkins.BuildStatus;
import com.hrs.shipit.illbeback.ssl_workaround.SSLWorkAround;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuildStatusParser {

    public ProjectStatus parseProject(String project) {
        SSLWorkAround.disableSslVerification();

        RestTemplate restTemplate = new RestTemplate();
        BuildStatus status = restTemplate
            .getForObject("https://ci.jenkins-ci.org/job/" + project + "/lastBuild/api/json?tree=duration,estimatedDuration,building", BuildStatus.class);

        return new ProjectStatus(project, status.getDuration() / status.getEstimatedDuration(), status
            .getEstimatedDuration(), status.getDuration(), status.isBuilding());
    }
}
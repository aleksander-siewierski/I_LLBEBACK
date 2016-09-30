package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.jenkins.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class JobParser {
    @Autowired RestTemplate restTemplate;

    private static final String JOB_PARAMETERS = "/api/json?tree=description,displayName,url,name,color,lastBuild[number,url]";

    Job parseJobStatus(String jobUrl) {
        return restTemplate.getForObject(jobUrl + JOB_PARAMETERS, Job.class);
    }
}

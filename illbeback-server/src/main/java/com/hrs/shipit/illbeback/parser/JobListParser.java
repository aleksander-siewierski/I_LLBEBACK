package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.jenkins.JobList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobListParser {
    @Autowired RestTemplate restTemplate;

    private static String PARAMETERS = "/api/json?pretty=true&tree=jobs[name,url,color]";

    public JobList parseJobList(String server) {
        return restTemplate.getForObject(server + PARAMETERS, JobList.class);
    }
}

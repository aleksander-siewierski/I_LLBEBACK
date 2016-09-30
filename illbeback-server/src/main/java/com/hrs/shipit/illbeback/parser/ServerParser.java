package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.ServerStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;
import com.hrs.shipit.illbeback.model.jenkins.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerParser {
    private static final Logger LOG = LoggerFactory.getLogger(ServerParser.class);
    private static final String SERVER_PARAMETERS = "/api/json?tree=jobs[name,url,color]";
    @Autowired RestTemplate restTemplate;
    @Autowired JobParser jobParser;
    public ServerStatus parseServerStatus(String server) {
        Server jenkinsObject = restTemplate.getForObject(server + SERVER_PARAMETERS, Server.class);
        jenkinsObject.setServerUrl(server);

        List<Job> jobs = jenkinsObject.getJobs().stream().map(j -> {
            try {
                return jobParser.parseJobStatus(j.getUrl());
            } catch (HttpClientErrorException e) {
                LOG.error("Failed to parse job " + j.getName(), e);
                return null;
            }
        }).filter(j -> j != null).collect(Collectors.toList());

        return new ServerStatus(server, jobs);
    }
}

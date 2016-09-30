package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.jenkins.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServerParser {
    @Autowired RestTemplate restTemplate;

    private static final String PARAMETERS = "/api/json?pretty=true&tree=jobs[name,url,color]";

    public Server parseServerInfo(String server) {
        return restTemplate.getForObject(server + PARAMETERS, Server.class);
    }
}

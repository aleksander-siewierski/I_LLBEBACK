package com.hrs.shipit.illbeback.configuration.controller;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.jenkins.JobList;
import com.hrs.shipit.illbeback.parser.JobListParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/configuration/", produces = "application/json")
public class ConfigurationController {

    @Autowired
    private ConfigurationService service;

    @Autowired
    private JobListParser parser;

    @RequestMapping(value = "server/add/", method = RequestMethod.POST)
    public JobList addServer(@RequestParam("url") String url) {
        return parser.parseJobList(url);
    }
}

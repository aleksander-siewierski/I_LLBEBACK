package com.hrs.shipit.illbeback.configuration.controller;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.jenkins.JobList;
import com.hrs.shipit.illbeback.parser.BuildStatusParser;
import com.hrs.shipit.illbeback.parser.JobListParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/configuration/", produces = "application/json")
public class ConfigurationController {

    @Autowired
    private ConfigurationService service;

    @Autowired
    private JobListParser jobListParser;

    @Autowired
    private BuildStatusParser buildStatusParser;

    @RequestMapping(value = "server/add/", method = RequestMethod.POST)
    public JobList addServer(@RequestParam("url") String url) {
        JobList jobList = jobListParser.parseJobList(url);
        service.addServer(url, jobList);

        return jobList;
    }

    @RequestMapping(value = "job/register/", method = RequestMethod.POST)
    public ResponseEntity<String> registerJob(@RequestParam("url") String url) {
        if (!service.getRegisteredJobs().contains(url)) {
            buildStatusParser.parseBuildStatusForJob(url);
            service.registerJob(url);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"error\": \"Job already registered\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "job/remove/", method = RequestMethod.POST)
    public ResponseEntity<String> removeJob(@RequestParam("url") String url) {
        if (service.getRegisteredJobs().contains(url)) {
            service.removeJob(url);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"error\": \"Job is not registered\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

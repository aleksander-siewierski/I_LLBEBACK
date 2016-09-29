package com.hrs.shipit.illbeback.configuration.controller;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.model.jenkins.JobList;
import com.hrs.shipit.illbeback.notifier.NotifierScheduledTask;
import com.hrs.shipit.illbeback.parser.BuildStatusParser;
import com.hrs.shipit.illbeback.parser.JobListParser;
import com.hrs.shipit.illbeback.updater.BuildStatusUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static javafx.scene.input.KeyCode.X;

@RestController
@RequestMapping(path = "api/configuration/", produces = "application/json")
public class ConfigurationController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationController.class);

    @Autowired
    private ConfigurationService service;

    @Autowired
    private JobListParser jobListParser;

    @Autowired
    private BuildStatusParser buildStatusParser;

    @Autowired
    private BuildStatusUpdater buildStatusUpdater;

    @RequestMapping(value = "server/add/", method = RequestMethod.POST)
    public JobList addServer(@RequestParam("url") String url) {
        JobList jobList = jobListParser.parseJobList(url);
        service.addServer(url, jobList);

        return jobList;
    }

    @RequestMapping(value = "server/", method = RequestMethod.GET)
    public Set<String> listServers() {
        return service.getServers().keySet();
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

    @RequestMapping(value = "job/", method = RequestMethod.GET)
    public List<String> listJobs() {
        return service.getRegisteredJobs();
    }

    @RequestMapping(value = "all/", method = RequestMethod.GET)
    public List<JobStatus> getAllJobsOnAllServers() {
        List<JobStatus> jobStatuses = buildStatusUpdater.updateStatuses();

        performUglyHacksThatDataIsPresentedBetter(jobStatuses);

        System.out.println("Result for all: " + jobStatuses);
        return jobStatuses;
    }

    private void performUglyHacksThatDataIsPresentedBetter(List<JobStatus> jobStatuses) {

        for (JobStatus jobStatus : jobStatuses) {
            String jobUrl = jobStatus.getJobName();

            try {
                String serverUrl = jobUrl.substring(0, jobUrl.indexOf("/", "http://".length() + 1)) + "/"; // ugly hacking
                jobStatus.setServerName(serverUrl);

                URL aURL = new URL(jobUrl);
                String path = aURL.getPath();
                String[] pathArray = path.split("/");
                String shortJobName = pathArray[2];

                jobStatus.setShortJobName(shortJobName);
            } catch (MalformedURLException ex) {
                LOG.error("MalformedURLException: " + jobUrl, ex);
            } catch (Throwable t) {
                LOG.error("Throwable!: ", t);
            }
        }
    }

}

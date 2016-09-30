package com.hrs.shipit.illbeback.configuration.controller;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.model.ServerStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;
import com.hrs.shipit.illbeback.parser.ServerParser;
import com.hrs.shipit.illbeback.updater.BuildStatusUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/configuration/", produces = "application/json")
public class ConfigurationController {

    @Autowired private ConfigurationService service;

    @Autowired private ServerParser serverParser;

    @Autowired private BuildStatusUpdater buildStatusUpdater;

    @RequestMapping(value = "server/add/", method = RequestMethod.POST)
    public ResponseEntity<ServerStatus> addServer(@RequestParam("url") String url) {
        if (url.charAt(url.length() - 1) != '/') {
            url += "/";
        }

        ServerStatus server = serverParser.parseServerStatus(url);
        service.addServer(url, server);

        return ResponseEntity.ok(server);
    }

    @RequestMapping(value = "server/remove/", method = RequestMethod.POST)
    public ResponseEntity<String> removeServer(@RequestParam("url") String url) {
        if (service.getServers().containsKey(url)) {
            service.removeServer(url);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"error\": \"Server is not registered\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "server/show/", method = RequestMethod.POST)
    public ResponseEntity<ServerStatus> showServer(@RequestParam("url") String url) {
        if (service.getServers().containsKey(url)) {
            return ResponseEntity.ok(service.getServers().get(url));
        }

        return new ResponseEntity<>(ServerStatus.empty(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "server/", method = RequestMethod.GET)
    public Set<String> listServers() {
        return service.getServers().keySet();
    }

    @RequestMapping(value = "job/register/", method = RequestMethod.POST)
    public ResponseEntity<String> registerJob(@RequestParam("url") String url) {
        Optional<Job> optional = service.findJobByJobUrl(url);

        if (optional.isPresent() && !service.containsJob(optional.get())) {
            service.registerJob(optional.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"error\": \"Job already registered\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "job/remove/", method = RequestMethod.POST)
    public ResponseEntity<String> removeJob(@RequestParam("url") String url) {
        Optional<Job> optional = service.findJobByJobUrl(url);

        if (optional.isPresent() && service.containsJob(optional.get())) {
            service.removeJob(optional.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("{\"error\": \"Job is not registered\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "job/", method = RequestMethod.GET)
    public List<String> listJobs() {
        return service.getRegisteredJobs().stream().map(Job::getUrl).collect(Collectors.toList());
    }

    @RequestMapping(value = "all/", method = RequestMethod.GET)
    public List<JobStatus> getAllJobsOnAllServers() {
        return buildStatusUpdater.updateStatuses();
    }
}

package com.hrs.shipit.illbeback.notifier;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.model.jenkins.Job;
import com.hrs.shipit.illbeback.model.jenkins.Server;
import com.hrs.shipit.illbeback.parser.ServerParser;
import com.hrs.shipit.illbeback.updater.BuildStatusUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotifierScheduledTask {

    private BuildStatusUpdater updater;

    @Autowired ServerParser serverParser;

    @Autowired
    ConfigurationService configurationService;

    @Autowired private SimpMessagingTemplate template;

    private boolean doInitFakeData = true;

    public NotifierScheduledTask(@Autowired BuildStatusUpdater updater) {
        this.updater = updater;
    }

    @Scheduled(fixedRate = 5000)

    public void fireCommunication() {
        if (doInitFakeData) {
            initFakeData();
        }

        List<JobStatus> jobStatuses = updater.updateStatuses();

        System.out.println("Sending: " + jobStatuses.toString());

        template.convertAndSend("/topic/greetings", jobStatuses);
    }

    private void initFakeData() {
        String detpuw1 = "http://detpuw1-jenkins-update-971.data.det:8081/";

        Server server = new Server();
        server.setServerUrl(detpuw1);
        List<Job> jobs = new ArrayList<>(2);
        Job job = new Job();
        job.setName("txs-build-frontend");
        String jobUrl = "http://detpuw1-jenkins-update-971.data.det:8081/job/txs-build-frontend/";
        job.setUrl(jobUrl);
        job.setColor("green");
        job.setServer(server);
        jobs.add(job);

        job.setName("build-webportal-build");
        String secondJobUrl = "http://detpuw1-jenkins-update-971.data.det:8081/job/build-webportal-build/";
        job.setUrl(secondJobUrl);
        job.setColor("green");
        jobs.add(job);
        job.setServer(server);

        server.setJobs(jobs);

        configurationService.addServer(detpuw1, server);
        configurationService.registerJob(jobUrl);
        configurationService.registerJob(secondJobUrl);
        server = new Server();

        String detpuw2 = "http://detpuw2-jenkins-update-971.data.det:8081/";
        server.setServerUrl(detpuw2);
        jobs = new ArrayList<>(2);
        job = new Job();
        job.setServer(server);
        job.setName("txs-build-frontend");
        jobUrl = "http://detpuw2-jenkins-update-971.data.det:8081/job/txs-build-frontend/";
        job.setUrl(jobUrl);
        job.setColor("green");
        jobs.add(job);

        job.setName("build-webportal-build");
        secondJobUrl = "http://detpuw2-jenkins-update-971.data.det:8081/job/build-webportal-build/";
        job.setUrl(secondJobUrl);
        job.setColor("green");
        jobs.add(job);
        job.setServer(server);

        server.setJobs(jobs);

        configurationService.addServer(detpuw2, server);
        configurationService.registerJob(jobUrl);
        configurationService.registerJob(secondJobUrl);

        doInitFakeData = false;
    }
}
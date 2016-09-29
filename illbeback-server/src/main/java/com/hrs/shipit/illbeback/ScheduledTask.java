package com.hrs.shipit.illbeback;

import com.hrs.shipit.illbeback.model.ProjectStatus;
import com.hrs.shipit.illbeback.updater.BuildStatusUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);

    private BuildStatusUpdater updater;

    @Autowired
    private SimpMessagingTemplate template;


    public ScheduledTask(@Autowired BuildStatusUpdater updater) {
        this.updater = updater;
        updater.registerProject("Core_Folder/job/jenkins_lts");
    }

    @Scheduled(fixedRate = 5000)
    public void fireCommunication() {
        List<ProjectStatus> projectStatuses = updater.updateStatuses();

        System.out.println("Sending: " + projectStatuses.toString());

        template.convertAndSend("/topic/greetings", projectStatuses);
    }
}
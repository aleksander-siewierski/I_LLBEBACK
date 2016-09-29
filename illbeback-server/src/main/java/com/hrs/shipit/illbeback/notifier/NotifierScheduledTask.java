package com.hrs.shipit.illbeback.notifier;

import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.updater.BuildStatusUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifierScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(NotifierScheduledTask.class);

    private BuildStatusUpdater updater;

    @Autowired private SimpMessagingTemplate template;

    public NotifierScheduledTask(@Autowired BuildStatusUpdater updater) {
        this.updater = updater;
    }

    @Scheduled(fixedRate = 5000)
    public void fireCommunication() {
        List<JobStatus> projectStatuses = updater.updateStatuses();

        System.out.println("Sending: " + projectStatuses.toString());

        template.convertAndSend("/topic/greetings", projectStatuses);
    }
}
package com.hrs.shipit.illbeback.notifier;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.parser.ServerParser;
import com.hrs.shipit.illbeback.updater.BuildStatusUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifierScheduledTask {

    @Autowired ServerParser serverParser;
    @Autowired ConfigurationService configurationService;
    private BuildStatusUpdater updater;
    @Autowired private SimpMessagingTemplate template;

    public NotifierScheduledTask(@Autowired BuildStatusUpdater updater) {
        this.updater = updater;
    }

    @Scheduled(fixedRate = 5000)
    public void fireCommunication() {
        List<JobStatus> jobStatuses = updater.updateStatuses();
        template.convertAndSend("/topic/greetings", jobStatuses);
    }

}
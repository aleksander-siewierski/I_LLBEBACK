package com.hrs.shipit.illbeback;

import com.hrs.shipit.illbeback.parser.ProjectStatusUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private ProjectStatusUpdater updater;

    public ScheduledTask(@Autowired ProjectStatusUpdater updater) {
        this.updater = updater;
//        updater.registerProject("txs-build-frontend");
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println(updater.updateStatuses().toString());
    }
}
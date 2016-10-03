package com.txs.notification.plugin.action;

import com.intellij.util.containers.HashMap;

import javax.swing.*;

/**
 * Created by jarek on 29.09.16.
 */
public class JobScheduleRunnableAction implements Runnable {
    private HashMap<String, Boolean> statusMap;
    private JList jobList;
    private JLabel urlValue;

    public JobScheduleRunnableAction(JList jobList, JLabel urlValue, HashMap<String, Boolean> statusMap) {
        this.statusMap = statusMap;
        this.jobList = jobList;
        this.urlValue = urlValue;
    }

    @Override
    public void run() {

        java.util.Timer timer = new java.util.Timer();

        timer.schedule(new UpdateJobListAction(jobList, urlValue, statusMap), 0, 6*1000);

    }
}

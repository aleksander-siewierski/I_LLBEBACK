package com.txs.notification.plugin.action;

import com.intellij.util.containers.*;
import com.intellij.util.containers.HashMap;
import com.txs.notification.plugin.NotificationWrapper;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

/**
 * Created by jarek on 29.09.16.
 */
public class jobScheduleRunnableAction implements Runnable {
    private HashMap<String, Boolean> statusMap;
    private DefaultListModel listModel;

    public jobScheduleRunnableAction(DefaultListModel listModel, HashMap<String, Boolean> statusMap) {
        this.statusMap = statusMap;
        this.listModel = listModel;
    }

    @Override
    public void run() {
        NotificationWrapper vo = new NotificationWrapper();

        java.util.Timer timer = new java.util.Timer();

        timer.schedule(new updateJobListAction(listModel, statusMap), 0, 10*1000);

    }
}

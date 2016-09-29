package com.txs.notification.plugin.action;

import com.txs.notification.plugin.NotificationWrapper;

import javax.swing.*;
import javax.swing.Timer;
import java.util.*;

/**
 * Created by jarek on 29.09.16.
 */
public class jobScheduleRunnableAction implements Runnable {
    private DefaultListModel listModel;

    public jobScheduleRunnableAction(DefaultListModel listModel) {
        this.listModel = listModel;
    }

    @Override
    public void run() {
        NotificationWrapper vo = new NotificationWrapper();

        java.util.Timer timer = new java.util.Timer();

        timer.schedule(new updateJobListAction(listModel), 0, 10*1000);

    }
}

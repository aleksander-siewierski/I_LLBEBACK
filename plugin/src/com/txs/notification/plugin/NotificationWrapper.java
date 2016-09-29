package com.txs.notification.plugin;

/**
 * Created by jarek on 29.09.16.
 */

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

public class NotificationWrapper {

    public static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup("I Will Be Back",
                    NotificationDisplayType.BALLOON, true);


    public void error(String message){
        notify(message, NotificationType.ERROR);
    }

    public void info(String message){
        notify(message, NotificationType.INFORMATION);
    }

    public void warning(String message){
        notify(message, NotificationType.WARNING);
    }

    public void notify(String message, NotificationType type) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification(message, type);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Notifications.Bus.notify(notification, projects[0]);
            }
        });
    }
}
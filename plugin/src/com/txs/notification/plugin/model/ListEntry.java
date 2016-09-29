package com.txs.notification.plugin.model;

import com.intellij.notification.Notification;
import com.txs.notification.plugin.NotificationWrapper;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by jarek on 29.09.16.
 */
public class ListEntry {
    private String shortJobName;
    private String serverName;
    private int completion;
    private long estimatedDuration;
    private long duration;
    private boolean building;
    private long timestamp;

    public String getShortJobName() {
        return shortJobName;
    }

    public void setShortJobName(String shortJobName) {
        this.shortJobName = shortJobName;
    }

    public String getServerName() {
        String name = serverName;
        name = name.substring(0, serverName.lastIndexOf(":"));
        name = name.replaceAll("http://", "");
        return name;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }


    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public long getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(long estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProgress(){
        long unixTime = System.currentTimeMillis();
        long duration = unixTime - timestamp;

        if (duration >= estimatedDuration){
            return "100%";
        }
        int result = (int) Math.round(((float)duration/(float)estimatedDuration)*(float)100);
        return Integer.toString(result)+"%";
    }

    @Override
    public String toString() {
        return "<html><font color='green'><b>"+getProgress()+"</b></font> - <b>"+getShortJobName()+"</b> - <font color='gray'>"+getServerName()+"</font></html>";
    }
}

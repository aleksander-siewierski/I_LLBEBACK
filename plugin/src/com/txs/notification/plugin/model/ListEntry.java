package com.txs.notification.plugin.model;

/**
 * Created by jarek on 29.09.16.
 */
public class ListEntry {
    String id;
    String jobName;
    String server;
    String progress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "<html><b>"+server+"</b> - "+jobName+" "+progress+"</html>";
    }
}

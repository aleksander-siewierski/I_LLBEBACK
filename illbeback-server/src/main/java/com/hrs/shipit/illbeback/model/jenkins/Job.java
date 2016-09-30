package com.hrs.shipit.illbeback.model.jenkins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrs.shipit.illbeback.model.ServerStatus;

public class Job {
    private String name;
    private String displayName;
    private String description;
    private String url;
    private String color;
    private Build lastBuild;
    @JsonIgnore private ServerStatus server;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Build getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(Build lastBuild) {
        this.lastBuild = lastBuild;
    }

    public ServerStatus getServer() {
        return server;
    }

    public void setServer(ServerStatus server) {
        this.server = server;
    }
}

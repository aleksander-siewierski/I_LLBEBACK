package com.hrs.shipit.illbeback.model.jenkins;

public class JobListJob {
    private String name;
    private String url;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

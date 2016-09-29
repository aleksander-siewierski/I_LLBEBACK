package com.hrs.shipit.illbeback.model;

/**
 * Created by asi03 on 2016-09-29.
 */
public class EnvironmentStatus {

    private final String name;
    private final boolean redeployed;

    public EnvironmentStatus(String name, boolean redeployed) {
        this.name = name;
        this.redeployed = redeployed;
    }

    public String getName() {
        return name;
    }

    public boolean getRedeployed() {
        return redeployed;
    }
}

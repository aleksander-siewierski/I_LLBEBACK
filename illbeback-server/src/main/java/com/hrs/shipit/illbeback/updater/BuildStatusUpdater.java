package com.hrs.shipit.illbeback.updater;

import com.hrs.shipit.illbeback.configuration.service.ConfigurationService;
import com.hrs.shipit.illbeback.model.JobStatus;
import com.hrs.shipit.illbeback.parser.BuildStatusParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BuildStatusUpdater {
    @Autowired private BuildStatusParser parser;

    @Autowired private ConfigurationService configuration;

    public List<JobStatus> updateStatuses() {
        return configuration.getRegisteredJobs().stream().map(parser::parseBuildStatusForJob).collect(toList());
    }

}

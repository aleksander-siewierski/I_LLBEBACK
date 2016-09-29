package com.hrs.shipit.illbeback.updater;

import com.hrs.shipit.illbeback.model.ProjectStatus;
import com.hrs.shipit.illbeback.parser.BuildStatusParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BuildStatusUpdater {
    @Autowired private BuildStatusParser parser;
    private List<String> registeredProjects = new ArrayList<>();

    public void registerProject(String project) {
        this.registeredProjects.add(project);
    }

    public List<ProjectStatus> updateStatuses() {
        return registeredProjects.stream().map(parser::parseProject).collect(toList());
    }

}

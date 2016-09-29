package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.model.ProjectStatus;
import com.hrs.shipit.illbeback.model.jenkins.BuildStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectStatusUpdater {
    private List<String> registeredProjects = new ArrayList<>();

    public void registerProject(String project) {
        this.registeredProjects.add(project);
    }

    public List<ProjectStatus> updateStatuses() {
        return registeredProjects.stream().map(this::parseProject).collect(toList());
    }

    private ProjectStatus parseProject(String project) {
        RestTemplate restTemplate = new RestTemplate();
        BuildStatus status = restTemplate
            .getForObject("" + project + "/lastBuild/api/json?tree=duration,estimatedDuration,building", BuildStatus.class);

        return new ProjectStatus(project, status.getDuration() / status.getEstimatedDuration(), status
            .getEstimatedDuration(), status.getDuration(), status.isBuilding());
    }
}

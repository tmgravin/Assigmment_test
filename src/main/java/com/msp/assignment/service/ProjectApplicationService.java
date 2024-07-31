package com.msp.assignment.service;

import com.msp.assignment.model.ProjectApplication;

public interface ProjectApplicationService {
    ProjectApplication applyForProject(Long projectId, Long doerId);
    ProjectApplication acceptProjectApplication(Long applicationId);
}

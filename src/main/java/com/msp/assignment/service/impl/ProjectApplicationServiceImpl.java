package com.msp.assignment.service.impl;

import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.repository.ProjectApplicationRepo;
import com.msp.assignment.service.ProjectApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectApplicationServiceImpl implements ProjectApplicationService {
    @Autowired
    private ProjectApplicationRepo projectApplicationRepo;

    @Override
    public ProjectApplication applyForProject(Long projectId, Long doerId) {
        return null;
    }

    @Override
    public ProjectApplication acceptProjectApplication(Long applicationId) {
        return null;
    }

    @Override
    public ProjectApplication getApplicationByProjectsId(Long id) {
        return projectApplicationRepo.findApplicationByProjectsId(id);
    }
}

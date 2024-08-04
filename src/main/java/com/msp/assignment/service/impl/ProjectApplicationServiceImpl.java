package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.ApplicationStatus;
import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.model.Projects;
import com.msp.assignment.repository.ProjectApplicationRepo;
import com.msp.assignment.repository.ProjectRepo;
import com.msp.assignment.service.ProjectApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

    @Autowired
    private ProjectApplicationRepo projectApplicationRepo;

    private static final Logger log = LoggerFactory.getLogger(ProjectApplicationServiceImpl.class);

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public ProjectApplication getApplicationByProjectsId(Long id) {
        return projectApplicationRepo.findApplicationByProjectsId(id);
    }

    @Override
    public List<ProjectApplication> getApplicationsByUsersId(Long usersId) {
        log.info("Inside getApplicationsByDoer method of ProjectServiceImpl (com.msp.assignment.service.impl)");

        List<Projects> projects = projectRepo.findByUsersId(usersId);

        // Find all ProjectApplications by doer
        List<ProjectApplication> allApplications = projectApplicationRepo.findByUsersId(usersId);
        log.debug("Fetched {} applications for doer with ID {}", allApplications.size(), usersId.getClass());

        // Filter the applications to include only those with ACCEPTED status
        List<ProjectApplication> acceptedApplications = allApplications.stream()
                .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
                .collect(Collectors.toList());
        log.debug("Filtered {} accepted applications for doer with ID {}", acceptedApplications.size(), usersId.getClass());

        return acceptedApplications;
    }
}

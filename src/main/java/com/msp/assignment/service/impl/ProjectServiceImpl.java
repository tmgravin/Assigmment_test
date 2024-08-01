package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.ApplicationStatus;
import com.msp.assignment.enumerated.ProjectStatus;
import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.model.Users;
import com.msp.assignment.repository.ProjectApplicationRepo;
import com.msp.assignment.repository.ProjectDetailsRepo;
import com.msp.assignment.repository.ProjectRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.ProjectService;
import com.msp.assignment.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProjectDetailsRepo projectDetailsRepo;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProjectApplicationRepo projectApplicationRepo;

    @Override
    public Projects addProject(Projects project, ProjectsDetails details, MultipartFile file) {
        log.info("Inside addProject method of ProjectServiceImpl (com.msp.assignment.service.impl)");

        try {
            // Save project first
            Projects savedProject = projectRepo.save(project);
            log.info("Project saved with ID: {}", savedProject.getId());

            // Handle file upload
            if (file != null && !file.isEmpty()) {
                // Generate a unique file name and save the file
                String filePath = fileUtils.generateFileName(file);  // Ensure fileUtils has the appropriate method for storing files
                fileUtils.saveFile(file, filePath);  // Save the file to the desired location
                details.setProjectUrl(filePath);  // Save the file path or URL to the database
                log.info("File uploaded and saved to path: {}", filePath);
            }

            // Set default project status to PENDING
            details.setProjectStatus(ProjectStatus.PENDING);

            // Set the project reference in projectDetails
            details.setProjects(savedProject);

            // Save project details
            projectDetailsRepo.save(details);
            log.info("Project details saved for project ID: {}", savedProject.getId());

            return savedProject;

        } catch (IOException e) {
            log.error("Error handling file upload", e);
            throw new RuntimeException("Error handling file upload", e);
        } catch (Exception e) {
            log.error("Error saving project or project details", e);
            throw new RuntimeException("Error saving project or project details", e);
        }
    }

    @Override
    public List<ProjectsDetails> getProjectDetailsByUserId(Long userId) {
        log.info("Inside getProjectDetailsByUserId method of ProjectServiceImpl (com.msp.assignment.service.impl)");
        return projectDetailsRepo.findProjectsDetailsByUserId(userId);
    }

    @Override
    public ProjectApplication applyForProject(Long projectId, Long doerId) {
        log.info("Inside applyForProject method of ProjectServiceImpl (com.msp.assignment.service.impl)");
        Projects project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        Users doer = usersRepository.findById(doerId).orElseThrow(() -> new RuntimeException("User not found"));

        ProjectApplication application = new ProjectApplication();
        application.setProjects(project);
        application.setDoer(doer);
        application.setStatus(ApplicationStatus.PENDING);

        return projectApplicationRepo.save(application);
    }

    @Override
    public ProjectApplication acceptProjectApplication(Long applicationId) {
        log.info("Inside acceptProjectApplication method of ProjectServiceImpl (com.msp.assignment.service.impl)");



        // Find the application to be accepted
        ProjectApplication acceptedApplication = projectApplicationRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));


        // Find the project related to the application
        Projects project = acceptedApplication.getProjects();

        // Find the project details
        ProjectsDetails projectsDetails = projectDetailsRepo.findByProjectsId(project.getId())
                .orElseThrow(() -> new RuntimeException("Project details not found"));

        // Set the project status to ON_GOING
        projectsDetails.setProjectStatus(ProjectStatus.ON_GOING);
        projectDetailsRepo.save(projectsDetails);

        // Update the status of the accepted application
        acceptedApplication.setStatus(ApplicationStatus.ACCEPTED);
        projectApplicationRepo.save(acceptedApplication);

        // Find all other pending applications for the same project
        List<ProjectApplication> otherApplications = projectApplicationRepo.findByProjectsAndStatus(project, ApplicationStatus.PENDING);

        // Update the status of these other applications to REJECTED
        for (ProjectApplication application : otherApplications) {
            if (!application.getId().equals(applicationId)) {
                application.setStatus(ApplicationStatus.REJECTED);
                projectApplicationRepo.save(application);
            }
        }

        return acceptedApplication;
    }

    @Override
    public List<ProjectApplication> getApplicationsByUsersId(Long usersId) {
        log.info("Inside getApplicationsByDoer method of ProjectServiceImpl (com.msp.assignment.service.impl)");

        Optional<Projects> projects = projectRepo.findByUsersId(usersId);

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

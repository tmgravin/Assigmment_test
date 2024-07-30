package com.msp.assignment.service.impl;

import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.repository.ProjectDetailsRepo;
import com.msp.assignment.repository.ProjectRepo;
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

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProjectDetailsRepo projectDetailsRepo;




    @Override
    public Projects addProject(Projects project, ProjectsDetails details, MultipartFile file) {
        log.info("Adding project with ID: {}", project.getId());

        try {
            // Save project first
            Projects savedProject = projectRepo.save(project);
            log.info("Project saved with ID: {}", savedProject.getId());



            // Set the project reference in projectDetails
            details.setProjects(savedProject);

            // Save project details
            projectDetailsRepo.save(details);

            // Handle file upload
            if (file != null && !file.isEmpty()) {
                // Generate a unique file name and save the file
                String filePath = fileUtils.generateFileName(file);  // Ensure fileUtils has the appropriate method for storing files
                fileUtils.saveFile(file, filePath);  // Save the file to the desired location
                details.setProjectUrl(filePath);  // Save the file path or URL to the database
                log.info("File uploaded and saved to path: {}", filePath);
            }
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
}
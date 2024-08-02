package com.msp.assignment.service.impl;

import com.msp.assignment.model.CompletedProject;
import com.msp.assignment.model.Projects;
import com.msp.assignment.repository.CompletedProjectRepo;
import com.msp.assignment.service.CompletedProjectService;
import com.msp.assignment.service.ProjectService;
import com.msp.assignment.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;

@Service
public class CompletedProjectServiceImpl implements CompletedProjectService {
    @Autowired
    private CompletedProjectRepo completedProjectRepo;

    @Autowired
    private FileUtils fileUtils;

    private static final Logger log = LoggerFactory.getLogger(CompletedProjectServiceImpl.class);

    @Override
    public CompletedProject addCompletedProject(Projects project, MultipartFile file) throws IOException {
        log.info("Inside addCompletedProject method of CompletedProjectServiceImpl");

        try {
            // Create a new CompletedProject entity and associate it with the given project
            CompletedProject completedProject = new CompletedProject();
            completedProject.setProject(project);

            // Handle file upload if a file is provided
            if (file != null && !file.isEmpty()) {
                String filePath = fileUtils.generateFileName(file);
                fileUtils.saveFile(file, filePath);
                completedProject.setFile(filePath);
                completedProjectRepo.save(completedProject);  // Update the entity with the file path
                log.info("File uploaded and saved to path: {}", filePath);
            }

            // Save the CompletedProject entity to the database
            CompletedProject savedCompletedProject = completedProjectRepo.save(completedProject);
            log.info("CompletedProject saved with ID: {}", savedCompletedProject.getId());



            return savedCompletedProject;
        } catch (IOException e) {
            log.error("Error handling file upload", e);
            throw new IOException("Error handling file upload", e);
        } catch (Exception e) {
            log.error("Error saving CompletedProject", e);
            throw new RuntimeException("Error saving CompletedProject", e);
        }
    }

    @Override
    public CompletedProject getById(Long id) {
        return null;
    }

    @Override
    public CompletedProject getCompletedProject() {
        return null;
    }
}

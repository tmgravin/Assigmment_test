package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.ProjectStatus;
import com.msp.assignment.enumerated.UserType;
import com.msp.assignment.model.*;
import com.msp.assignment.repository.CompletedProjectRepo;
import com.msp.assignment.repository.PaymentRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.CompletedProjectService;
import com.msp.assignment.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CompletedProjectServiceImpl implements CompletedProjectService {
    @Autowired
    private CompletedProjectRepo completedProjectRepo;

    @Autowired
    private FileUtils fileUtils;

    @Value("${S3_BASE_URL}")
    private String s3BaseUrl;

    private static final Logger log = LoggerFactory.getLogger(CompletedProjectServiceImpl.class);
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public CompletedProject addCompletedProject(Projects projectId, MultipartFile file, Users doerId) throws IOException {
        log.info("Inside addCompletedProject method of CompletedProjectServiceImpl");

        try {
            // Create a new CompletedProject entity and associate it with the given project
            CompletedProject completedProject = new CompletedProject();
            completedProject.setProject(projectId);
            completedProject.setDoer(doerId);

            String filePath = fileUtils.generateFileName(file);
            completedProject.setFile(s3BaseUrl+filePath);

            // Save the CompletedProject entity to the database
            CompletedProject savedCompletedProject = completedProjectRepo.save(completedProject);
            log.info("CompletedProject saved with ID: {}", savedCompletedProject.getId());

            // Update project status on project details
            ProjectsDetails projectsDetails = new ProjectsDetails();
            projectsDetails.setProjectStatus(ProjectStatus.COMPLETED);

            // Handle file upload if a file is provided
            if (file != null && !file.isEmpty()) {
                fileUtils.saveFile(file, filePath);
                log.info("File uploaded and saved to path: {}", filePath);
            }

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
    public String getCompletedProjectById(Long userId, Long completedProjectId) {
        try {
            // Fetch user and Completed Project
            Users user = usersRepository.findById(userId).orElse(null);
            CompletedProject completedProject = completedProjectRepo.findById(completedProjectId).orElse(null);

            if (user == null || completedProject == null) {
                return null;
            }

            Projects projects = completedProject.getProject();
            Users projectCreator = projects.getUsers();
            Users doer = completedProject.getDoer();

            // Check User Role Permissions
            if (user.getUserType().equals(UserType.ADMIN) ||
                    user.getUserType().equals(UserType.ASSIGNMENT_DOER) ||
                    user.getUserType().equals(UserType.ASSIGNMENT_CREATOR)) {

                // Assignment creator specific logic
                if (user.getUserType().equals(UserType.ASSIGNMENT_CREATOR)) {
                    Payments payments = paymentRepo.findByProjectsID(projects.getId()).get(0);
                    if (payments != null && "COMPLETE".equals(payments.getProjects().getPaymentStatus())) {
                        return completedProject.getFile();
                    } else {
                        log.error("Payment not found or incomplete");
                        return null;
                    }
                }

                // Admin and assignment doer logic
                if (user.getUserType().equals(UserType.ADMIN) || user.getUserType().equals(UserType.ASSIGNMENT_DOER)) {
                    return completedProject.getFile();
                }
            }
            return null; // In case no condition matches
        } catch (Exception e) {
            log.error("An error occurred while fetching the completed project", e);
            throw new RuntimeException("Internal Server Error: " + e.getLocalizedMessage());
        }
    }

}

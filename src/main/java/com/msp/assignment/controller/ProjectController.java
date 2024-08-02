package com.msp.assignment.controller;

import com.msp.assignment.message.ApiResponse;
import com.msp.assignment.model.*;
import com.msp.assignment.service.ProjectDetailsService;
import com.msp.assignment.service.ProjectService;
import com.msp.assignment.service.UsersService;
import com.msp.assignment.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProjectDetailsService projectDetailsService;

    @Autowired
    private UsersService usersService;

    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    // Method for getting project details by creator ID
    @GetMapping("/byUser")
    public ResponseEntity<List<ProjectsDetails>> getProjectDetailsByUserId(@RequestParam(name = "userId", required = true) Long userId) {
        log.info("Inside getProjectDetailsByUserId method of ProjectController");
        try {
            List<ProjectsDetails> projectDetails = projectService.getProjectDetailsByUserId(userId);
            log.debug("Fetched {} project details for user ID {}", projectDetails.size(), userId);
            return ResponseEntity.ok(projectDetails);
        } catch (Exception e) {
            log.error("Error fetching project details by user ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Method for getting all project details or project details by ID
    @GetMapping("/")
    public ResponseEntity<?> getProjectDetails(@RequestParam(name = "id", required = false) Long id) {
        log.info("Inside getProjectDetails method of ProjectController");
        try {
            if (id != null) {
                log.debug("Fetching project details for ID {}", id);
                return ResponseEntity.ok(projectDetailsService.get(id));
            } else {
                log.debug("Fetching all project details");
                List<ProjectsDetails> allProjectDetails = projectDetailsService.getAll().stream().map(projectDetails -> {
                    projectDetails.getProjects().getUsers();
                    return projectDetails;
                }).collect(Collectors.toList());

                return ResponseEntity.status(HttpStatus.OK).body(allProjectDetails);
            }
        } catch (Exception e) {
            log.error("Error fetching project details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    // Controller method
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Projects> addProjectWithDetails(
            @RequestParam("scope") String scope,
            @RequestParam("experienceYear") String experienceYear,
            @RequestParam("levelOfExperience") String levelOfExperience,
            @RequestParam("projectUrl") MultipartFile projectUrl,
            @RequestParam("projectName") String projectName,
            @RequestParam("projectAmount") String projectAmount,
            @RequestParam("projectDeadline") Date projectDeadline,
            @RequestParam("users") Users users,
            @RequestParam("budgets") String budgets,
            @RequestParam("projectCategory") ProjectCategory projectCategory) {
        log.info("Inside addProjectWithDetails method of ProjectController");
        try {
            Projects savedProject = projectService.addProject(scope, experienceYear, levelOfExperience, projectUrl, projectName, projectAmount, projectDeadline, users, budgets, projectCategory);
            log.info("Project created successfully with ID: {}", savedProject.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
        } catch (IllegalArgumentException e) {
            log.error("Error parsing request parameters", e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Error handling file or saving project", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Method to apply for a project
    @PostMapping("/apply")
    public ResponseEntity<ProjectApplication> applyForProject(@RequestParam("projectId") Long projectId,
                                                              @RequestParam("doerId") Long doerId) {
        log.info("Inside applyForProject method of ProjectController");
        try {
            ProjectApplication application = projectService.applyForProject(projectId, doerId);
            log.info("Application created successfully with ID: {}", application.getId());
            ApiResponse<ProjectApplication> response = new ApiResponse<>(true, "Applied Successfully", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(application);
        } catch (Exception e) {
            ApiResponse<ProjectApplication> response = new ApiResponse<>(false, "Applied Failed", null);
            log.error("Error applying for project", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Method to accept a project application
    @PostMapping("/acceptApplication")
    public ResponseEntity<ProjectApplication> acceptApplication(@RequestParam("applicationId") Long applicationId) {
        log.info("Inside acceptApplication method of ProjectController");
        try {
            ProjectApplication application = projectService.acceptProjectApplication(applicationId);
            log.info("Application accepted successfully with ID: {}", application.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(application);
        } catch (Exception e) {
            log.error("Error accepting project application", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Method to get project applications by doer ID where application status is ACCEPTED
    @GetMapping("/doer")
    public ResponseEntity<List<ProjectApplication>> getApplicationsByDoerAndStatus(@RequestParam Long doer) {
        log.info("Inside getApplicationsByDoerAndStatus method of ProjectController");
        try {
            // Fetch and return the applications
            List<ProjectApplication> applications = projectService.getApplicationsByUsersId(doer);
            log.info("Fetched {} applications for doer ID {}", applications.size(), doer);
            return ResponseEntity.status(HttpStatus.OK).body(applications);
        } catch (IllegalArgumentException e) {
            log.error("User not found with ID: {}", doer, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error fetching applications by doer ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/total-projects")
    public ResponseEntity<?> countAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.countAllProjects());
    }

    @DeleteMapping("/delete")
    public void deteProject(Long id) throws IOException {
        projectService.deleteProject(id);
    }
}

package com.msp.assignment.controller;


import com.msp.assignment.enumerated.ExperienceYear;
import com.msp.assignment.enumerated.LevelOfExperience;
import com.msp.assignment.enumerated.ProjectStatus;
import com.msp.assignment.enumerated.Scope;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.model.Users;
import com.msp.assignment.service.ProjectDetailsService;
import com.msp.assignment.service.ProjectService;
import com.msp.assignment.utils.FileUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProjectDetailsService projectDetailsService;


    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping("/byUser")
    public ResponseEntity<List<ProjectsDetails>> getProjectDetailsByUserId(@RequestParam(name = "userId", required = true) Long userId) {
        List<ProjectsDetails> projectDetails = projectService.getProjectDetailsByUserId(userId);
        return ResponseEntity.ok(projectDetails);
    }

    @GetMapping("/")
    public ResponseEntity<?> getProjectDetails(@RequestParam(name = "id", required = false) Long id) {
        try {
            if (id != null) {
              return ResponseEntity.ok(projectDetailsService.get(id));

            } else {
                List<ProjectsDetails> allProjectDetails = projectDetailsService.getAll().stream().map(ProjectsDetails ->{
//                    ProjectsDetails.setProjects(null);
                    ProjectsDetails.getProjects().getUsers();
                    return ProjectsDetails;
                }).collect(Collectors.toList());

                return ResponseEntity.ok(allProjectDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }


    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Projects> addProjectWithDetails(@RequestParam("projectStatus") String projectStatus,
                                                          @RequestParam("scope") String scope,
                                                          @RequestParam("experienceYear") String experienceYear,
                                                          @RequestParam("levelOfExperience") String levelOfExperience,
                                                          @RequestParam("projectUrl") MultipartFile projectUrl,
                                                          @RequestParam("projectName") String projectName,
                                                          @RequestParam("projectAmount") String projectAmount,
                                                          @RequestParam("projectDeadline") Timestamp projectDeadline,
                                                          @RequestParam("users") Users users) {
        log.info("Inside addProjectWithDetails method of ProjectController");
        try {

            // Create and set project details
            ProjectsDetails projectsDetails = new ProjectsDetails();
            projectsDetails.setProjectStatus(ProjectStatus.valueOf(projectStatus.trim())); // Trim the input to remove any extra spaces
            projectsDetails.setScope(Scope.valueOf(scope.trim())); // Trim the input to remove any extra spaces
            projectsDetails.setExperienceYear(ExperienceYear.valueOf(experienceYear.trim())); // Trim the input to remove any extra spaces
            projectsDetails.setLevelOfExperience(LevelOfExperience.valueOf(levelOfExperience.trim())); // Trim the input to remove any extra spaces

            Projects projects = new Projects();
            projects.setUsers(users);
            projects.setProjectName(projectName);
            projects.setProjectAmount(projectAmount);
            projects.setProjectDeadline(Timestamp.valueOf(projectDeadline.toString()));

            // Add project with details
            Projects savedProject = projectService.addProject(projects, projectsDetails, projectUrl);

            // Return the saved project
            return ResponseEntity.ok(savedProject);

        } catch (IllegalArgumentException e) {
            log.error("Error parsing request parameters", e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Error handling file or saving project", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

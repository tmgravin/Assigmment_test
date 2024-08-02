package com.msp.assignment.controller;

import com.msp.assignment.model.CompletedProject;
import com.msp.assignment.model.Projects;
import com.msp.assignment.service.CompletedProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/competed/prject")
public class CompletedProjectController {
    @Autowired
    private CompletedProjectService completedProjectService;
    private static final Logger log = LoggerFactory.getLogger(CompletedProjectController.class);

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CompletedProject> addCompleetedProject(@RequestParam("project") Projects project, @RequestParam("file") MultipartFile file) {
        log.info("Inside addCompleetedProject method of CompletedProjectContrller");
        try {
            CompletedProject completedProject = completedProjectService.addCompletedProject(project, file);
            log.info("Add Completed Project Successfully {}", completedProject.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(completedProject);
        } catch (Exception e) {
            e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

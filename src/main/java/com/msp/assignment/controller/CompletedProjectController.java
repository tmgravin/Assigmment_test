package com.msp.assignment.controller;


import com.msp.assignment.model.Projects;
import com.msp.assignment.model.Users;
import com.msp.assignment.service.CompletedProjectService;
import com.msp.assignment.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/completed/project")
public class CompletedProjectController {
    @Autowired
    private CompletedProjectService completedProjectService;


    private static final Logger log = LoggerFactory.getLogger(CompletedProjectController.class);


        @Autowired
        private UsersService userService;

        @PostMapping("/")
        public ResponseEntity<String> addCompletedProject(@RequestParam("projectId") Projects projectId,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam("doerId") Users doerId) throws IOException {

            completedProjectService.addCompletedProject(projectId, file, doerId);

            return ResponseEntity.ok("Completed project added successfully.");
        }


    @GetMapping("/")
    public ResponseEntity<String> getCompletedProjectById(@RequestParam Long userId, @RequestParam Long completedProjectId) {
        try {
            String file = completedProjectService.getCompletedProjectById(userId, completedProjectId);
            if (file != null) {
                return ResponseEntity.ok(file);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    }

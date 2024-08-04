package com.msp.assignment.controller;

import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.service.ProjectApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/application")
public class ProjectApplicationController {

    @Autowired
    private ProjectApplicationService projectApplicationService;

    @GetMapping("/")
    public ResponseEntity<ProjectApplication> getApplicationByProjectId(@RequestParam(name = "id", required = false) Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(projectApplicationService.getApplicationByProjectsId(id));
    }
}

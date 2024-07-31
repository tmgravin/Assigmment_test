package com.msp.assignment.service;

import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Projects addProject(Projects projects, ProjectsDetails projectsDetails, MultipartFile projectUrl);
}
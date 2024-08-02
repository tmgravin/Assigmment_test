package com.msp.assignment.service;


import com.msp.assignment.model.CompletedProject;
import com.msp.assignment.model.Projects;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompletedProjectService {
    CompletedProject addCompletedProject(Projects project, MultipartFile file) throws IOException;

    CompletedProject getById(Long id);

    CompletedProject getCompletedProject();
}

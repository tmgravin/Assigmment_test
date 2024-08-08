package com.msp.assignment.service;


import com.msp.assignment.model.CompletedProject;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompletedProjectService {
    CompletedProject addCompletedProject(Projects projectId, MultipartFile file, Users doerId) throws IOException;
    String getCompletedProjectById(Long userId, Long completedProjectId);
}

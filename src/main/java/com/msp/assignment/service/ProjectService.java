package com.msp.assignment.service;

import com.msp.assignment.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;


public interface ProjectService {
//    Projects addProject(Projects projects, ProjectsDetails projectsDetails, MultipartFile projectUrl);

    Projects addProject(
            String scope, String experienceYear, String levelOfExperience,
            MultipartFile projectUrl, String projectName, String projectAmount,
            Date projectDeadline, Users users, String budgets, ProjectCategory projectCategory);

    List<ProjectsDetails> getProjectDetailsByUserId(Long userId);
    ProjectApplication applyForProject(Long projectId, Long doerId);

    ProjectApplication acceptProjectApplication(Long applicationId);

    List<ProjectApplication> getApplicationsByUsersId(Long usersId);


    Long countAllProjects();

    void deleteProject(Long id) throws IOException;
}

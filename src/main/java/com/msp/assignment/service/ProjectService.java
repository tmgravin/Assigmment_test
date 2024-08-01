package com.msp.assignment.service;

import com.msp.assignment.enumerated.ApplicationStatus;
import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.model.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProjectService {
    Projects addProject(Projects projects, ProjectsDetails projectsDetails, MultipartFile projectUrl);
    List<ProjectsDetails> getProjectDetailsByUserId(Long userId);
    ProjectApplication applyForProject(Long projectId, Long doerId);

    ProjectApplication acceptProjectApplication(Long applicationId);
//    List<Projects> getProjectsByDoerId(Long doerId);


    List<ProjectApplication> getApplicationsByDoer(Users doer);
}

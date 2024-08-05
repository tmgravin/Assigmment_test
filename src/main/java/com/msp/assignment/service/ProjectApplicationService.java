package com.msp.assignment.service;

import com.msp.assignment.model.ProjectApplication;

import java.util.List;

public interface ProjectApplicationService {

    ProjectApplication getApplicationByProjectsId(Long id);

    List<ProjectApplication> getApplicationsByUsersId(Long usersId);

}

package com.msp.assignment.service.impl;

import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.repository.ProjectDetailsRepo;
import com.msp.assignment.service.ProjectDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectDetailsServiceImpl implements ProjectDetailsService {

    @Autowired
    private ProjectDetailsRepo projectDetailsRepo;

    @Override
    public Optional<ProjectsDetails> get(Long id)
    {
    	Optional<ProjectsDetails> projectsDetails = projectDetailsRepo.findById(id);
    	projectsDetails.get().getProjects().setUsers(null);
        return projectsDetails;
    }

    @Override
    public List<ProjectsDetails> getAll()
    {
    	List<ProjectsDetails> allProjectDetails = projectDetailsRepo.findAll();
    	allProjectDetails.stream().map(ProjectsDetails ->
    	{
          ProjectsDetails.getProjects().setUsers(null);
          return ProjectsDetails;
    	}).collect(Collectors.toList());
        return allProjectDetails;
    }
}

package com.msp.assignment.service.impl;

import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.model.Users;
import com.msp.assignment.repository.ProjectDetailsRepo;
import com.msp.assignment.repository.ProjectRepo;
import com.msp.assignment.repository.UsersRepository;
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

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Optional<ProjectsDetails> get(Long id)
    {
        Optional<ProjectsDetails> projectsDetailsOptional = projectDetailsRepo.findById(id);
        if (projectsDetailsOptional.isPresent()) {
            ProjectsDetails projectsDetails = projectsDetailsOptional.get();
            Projects projects = projectsDetails.getProjects();
            if (projects != null) {
                Users users = projects.getUsers(); // Initialize lazy-loaded Users
            }
            return Optional.of(projectsDetails);
        } else {
            return Optional.empty();
        }
//    	Optional<ProjectsDetails> projectsDetails = projectDetailsRepo.findById(id);
//    	projectsDetails.get().getProjects().setUsers(null);
//        return projectsDetails;
    }

    @Override
    public List<ProjectsDetails> getAll()
    {List<ProjectsDetails> allProjectDetails = projectDetailsRepo.findAll();
        allProjectDetails.forEach(projectDetails -> {
            Projects projects = projectDetails.getProjects();
            if (projects != null) {
                Users users = projects.getUsers(); // Initialize lazy-loaded Users
            }
        });
        return allProjectDetails;
//    	List<ProjectsDetails> allProjectDetails = projectDetailsRepo.findAll();
//    	allProjectDetails.stream().map(ProjectsDetails ->
//    	{
//          ProjectsDetails.getProjects().setUsers(null);
//          return ProjectsDetails;
//    	}).collect(Collectors.toList());
//        return allProjectDetails;
    }
}

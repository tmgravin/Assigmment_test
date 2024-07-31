package com.msp.assignment.service.impl;

import com.msp.assignment.model.ProjectsDetails;
import com.msp.assignment.repository.ProjectDetailsRepo;
import com.msp.assignment.service.ProjectDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PrjectDetailsServiceImpl implements ProjectDetailsService {

    @Autowired
    private ProjectDetailsRepo projectDetailsRepo;

    @Override
    public Optional<ProjectsDetails> get(Long id) {
        return projectDetailsRepo.findById(id);
    }

    @Override
    public List<ProjectsDetails> getAll() {
        return projectDetailsRepo.findAll();
    }
}

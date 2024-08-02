package com.msp.assignment.service.impl;

import com.msp.assignment.model.ProjectCategory;
import com.msp.assignment.repository.ProjectCategoryRepo;
import com.msp.assignment.service.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectCategoryServiceImpl implements ProjectCategoryService {
    @Autowired
    private ProjectCategoryRepo projectCategoryRepo;


    @Override
    public List<ProjectCategory> getCategory() {
        return projectCategoryRepo.findAll();
    }

    @Override
    public Optional<ProjectCategory> getById(Long id) {
        return projectCategoryRepo.findById(id);
    }
}

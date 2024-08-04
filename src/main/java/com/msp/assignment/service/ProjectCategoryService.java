package com.msp.assignment.service;

import com.msp.assignment.model.ProjectCategory;

import java.util.List;
import java.util.Optional;

public interface ProjectCategoryService {
    List<ProjectCategory> getCategory();

    Optional<ProjectCategory> getById(Long id);
}

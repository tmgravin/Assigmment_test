package com.msp.assignment.service;

import com.msp.assignment.model.ProjectsDetails;

import java.util.List;
import java.util.Optional;

public interface ProjectDetailsService {
    Optional<ProjectsDetails> get(Long id);
    List<ProjectsDetails> getAll();
}

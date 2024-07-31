package com.msp.assignment.repository;

import com.msp.assignment.model.ProjectApplication;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectApplicationRepo extends JpaRepository<ProjectApplication, Long> {
    @EntityGraph(attributePaths = {"projects", "doer"})
    Optional<ProjectApplication> findById(Long id);
}

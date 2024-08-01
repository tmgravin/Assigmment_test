package com.msp.assignment.repository;

import com.msp.assignment.enumerated.ApplicationStatus;
import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectApplicationRepo extends JpaRepository<ProjectApplication, Long> {
    @EntityGraph(attributePaths = {"projects", "doer"})
    Optional<ProjectApplication> findById(Long id);

    List<ProjectApplication> findByProjectsAndStatus(Projects project, ApplicationStatus status);

    List<ProjectApplication> findByDoer(Users doer);
}


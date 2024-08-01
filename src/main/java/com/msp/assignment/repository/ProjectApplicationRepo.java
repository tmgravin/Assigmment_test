package com.msp.assignment.repository;

import com.msp.assignment.enumerated.ApplicationStatus;
import com.msp.assignment.model.ProjectApplication;
import com.msp.assignment.model.Projects;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectApplicationRepo extends JpaRepository<ProjectApplication, Long> {
    @EntityGraph(attributePaths = {"projects", "doer"})
    Optional<ProjectApplication> findById(Long id);

    List<ProjectApplication> findByProjectsAndStatus(Projects project, ApplicationStatus status);

    @Query(value = "SELECT pa.id, pa.project_id, pa.doer_id,pa.status,pa.created_at, pa.updated_at  FROM project_applications pa where pa.doer_id=:doer", nativeQuery = true)
    List<ProjectApplication> findByUsersId(@Param("doer") Long doer);
}


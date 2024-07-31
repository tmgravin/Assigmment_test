package com.msp.assignment.repository;


import com.msp.assignment.model.ProjectsDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectDetailsRepo extends JpaRepository<ProjectsDetails, Long> {
    @EntityGraph(attributePaths = {"projects", "projects.users"})
    Optional<ProjectsDetails> findById(Long id);

    @EntityGraph(attributePaths = {"projects", "projects.users"})
    List<ProjectsDetails> findAll();

    @Query("SELECT pd FROM ProjectsDetails pd JOIN FETCH pd.projects p JOIN FETCH p.users WHERE p.users.id = :userId")
    List<ProjectsDetails> findProjectsDetailsByUserId(@Param("userId") Long userId);
}

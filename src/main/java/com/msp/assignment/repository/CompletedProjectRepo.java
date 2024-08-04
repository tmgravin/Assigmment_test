package com.msp.assignment.repository;

import com.msp.assignment.model.CompletedProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedProjectRepo extends JpaRepository<CompletedProject, Long> {
    @Query("SELECT cp FROM CompletedProject cp WHERE cp.project.id = :projectId")
    CompletedProject findByProjectsId(@Param("projectId") Long projectId);}

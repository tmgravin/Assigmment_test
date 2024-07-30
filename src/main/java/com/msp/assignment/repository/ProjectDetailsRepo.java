package com.msp.assignment.repository;


import com.msp.assignment.model.ProjectsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDetailsRepo extends JpaRepository<ProjectsDetails, Long> {
}

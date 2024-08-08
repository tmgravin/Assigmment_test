package com.msp.assignment.repository;

import com.msp.assignment.model.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ProjectCategoryRepo extends JpaRepository<ProjectCategory, Long> {
}

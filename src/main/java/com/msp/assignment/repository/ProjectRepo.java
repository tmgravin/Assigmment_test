package com.msp.assignment.repository;

import com.msp.assignment.model.Projects;
import com.msp.assignment.model.ProjectsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Projects, Long> {
    List<Projects> findByUsersId(Long doer);

    @Query("SELECT COUNT(p) FROM Projects p")
    Long countAllProjects();
}

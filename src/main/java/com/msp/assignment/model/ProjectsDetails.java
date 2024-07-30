package com.msp.assignment.model;
import com.msp.assignment.enumerated.ExperienceYear;
import com.msp.assignment.enumerated.LevelOfExperience;
import com.msp.assignment.enumerated.ProjectStatus;
import com.msp.assignment.enumerated.Scope;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "projects_details")
@Data
public class ProjectsDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", nullable = false)
    private ProjectStatus projectStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    private Scope scope;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_year", nullable = false)
    private ExperienceYear experienceYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_of_experience", nullable = false)
    private LevelOfExperience levelOfExperience;

    @Column(name = "project_url", nullable = false, columnDefinition = "VARCHAR(255)")
    private String projectUrl;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id", referencedColumnName = "id")
    private Projects projects;
}
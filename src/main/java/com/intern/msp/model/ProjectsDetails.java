package com.intern.msp.model;

import com.intern.msp.Enum.*;
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

    //    @Enumerated
    //    @Column(name = "project_status",columnDefinition = " default 'PENDING'", nullable = false)
    //    private ProjectStatus projectStatus;

    @Enumerated(EnumType.STRING) // Persist the enum as a string
    @Column(name = "project_status", nullable = false)
    private ProjectStatus projectStatus;

    @Enumerated
    @Column(name = "scope", nullable = false)
    private Scope scope;

    @Enumerated
    @Column(name = "experience_year", nullable = false)
    private ExperienceYear experienceYear;

    @Enumerated
    @Column(name = "level_of_experience", nullable = false)
    private LevelOfExperience levelOfExperience;

    @Column(name = "project_url", nullable = false, columnDefinition = "VARCHAR(255)")
    private String projectUrl;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id", referencedColumnName = "id")
    private Projects projects;
}
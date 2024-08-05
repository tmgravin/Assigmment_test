package com.msp.assignment.model;

import com.msp.assignment.enumerated.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="project_applications")
public class ProjectApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Projects projects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doer_id", referencedColumnName = "id")
    private Users doer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private ApplicationStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;
}

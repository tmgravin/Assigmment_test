package com.msp.assignment.model;

import com.msp.assignment.enumerated.WorkType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "users_details")
@Data
public class UsersDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "portfolio", nullable = false, columnDefinition = "TEXT")
    private String portfolioFile;

    @Column(name = "cv", nullable = false, columnDefinition = "TEXT")
    private String cv;

    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "upload_photo", nullable = false, columnDefinition = "TEXT")
    private String uploadPhoto;

    @Column(name = "is_emailVerified", nullable = false, columnDefinition = "CHAR(1) default 'N'")
    private char isEmailVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", nullable = false)
    private WorkType workType;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

}

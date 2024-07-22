package com.intern.msp.model;

import com.intern.msp.Enum.WorkType;
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

    @Column(name = "portfolio", nullable = false,columnDefinition = "TEXT")
    private String portfolioFile;

    @Column(name = "cv", nullable = false, columnDefinition = "TEXT")
    private String cvText;

    @Column(name = "cover_letter",columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "upload_photo", nullable = false, columnDefinition = "TEXT")
    private String uploadPhoto;

    @Column(name = "is_emailVerified", nullable = false, columnDefinition = "CHAR(1) default 'N'")
    private char isEmailVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", nullable = false)
    private WorkType workType;

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",updatable = false)
    private Timestamp updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private Users users;

}

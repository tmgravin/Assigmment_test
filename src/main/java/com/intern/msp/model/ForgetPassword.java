package com.intern.msp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "forget_password")
@Data
public class ForgetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code",columnDefinition = "INTEGER(6)",nullable = false)
    private int code;

    @Column(name = "created_at",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",updatable = false)
    private Timestamp createdAt;

    @Column(name = "expired_at",nullable = false)
    private Timestamp expiredAt;

    @Column(name = "is_verified",columnDefinition = "CHAR(1) DEFAULT 'N'")
    private char isVerified;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;
}

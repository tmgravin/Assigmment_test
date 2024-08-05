package com.msp.assignment.model;

import com.msp.assignment.enumerated.LoginType;
import com.msp.assignment.enumerated.UserType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String email;

    @Column(name = "is_emailVerified", columnDefinition = "CHAR(1) default 'N'", nullable = false)
    private char isEmailVerified;

    @Column(name = "password", columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;

    @Column(name = "phone", length = 25, nullable = false)
    private String phone;

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", columnDefinition = "VARCHAR(255)", nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", columnDefinition = "VARCHAR(255)")
    private LoginType loginType;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;
}

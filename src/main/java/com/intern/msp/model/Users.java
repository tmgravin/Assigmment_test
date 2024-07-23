package com.intern.msp.model;

import com.intern.msp.eum.LoginType;
import com.intern.msp.eum.UserType;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name ="users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "email", unique = true, nullable = false,columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "password", nullable = false,columnDefinition = "VARCHAR(100)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", columnDefinition = "VARCHAR(255)", nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "register_type")
    private LoginType loginType;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updatedAt;

}

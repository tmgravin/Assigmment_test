package com.msp.assignment.model;

import com.msp.assignment.enumerated.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "CompletedProject")
public class CompletedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Projects project;


    @Column(name = "file", columnDefinition = "TEXT", nullable = false)
    private String file;

    @Column(name = "completion_date", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp completionDate;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false)
    private Timestamp updatedAt;
}

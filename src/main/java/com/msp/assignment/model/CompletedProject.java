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

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "File", columnDefinition = "TEXT")
    private String File;

    @Column(name = "completion_date", nullable = false)
    private Timestamp completionDate;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
}

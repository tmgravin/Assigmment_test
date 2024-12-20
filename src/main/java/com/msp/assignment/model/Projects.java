package com.msp.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.msp.assignment.enumerated.Budgets;
import com.msp.assignment.enumerated.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "projects")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String projectName;

    @Column(name = "project_amount", nullable = false, columnDefinition = "VARCHAR(20)")
    private String projectAmount;

    @Column(name = "project_deadline", nullable = false)
    private Date projectDeadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "budgets")
    private Budgets budgets;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ProjectCategory projectCategory;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status", nullable = false)
    private PaymentStatus paymentStatus;

}

package com.msp.assignment.model;

import com.msp.assignment.enumerated.Budgets;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "projects_budgets")
@Data
public class ProjectsBudgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "budgets", nullable = false)
    private Budgets budgets;


    @Column(name = "from_budgets", nullable = false, precision = 10, scale = 2)
    private BigDecimal fromBudgets;

    @Column(name = "to_budgets", nullable = false, precision = 10, scale = 2)
    private BigDecimal toBudgets;


    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    private Projects project;

}

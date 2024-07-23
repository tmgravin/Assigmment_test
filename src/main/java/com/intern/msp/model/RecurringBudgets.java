package com.intern.msp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "recurring_budgets")
@Data
public class RecurringBudgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recurring_payment",columnDefinition = "CHAR(1) DEFAULT 'N'",nullable = false)
    private char recurringPayment;

    @Column(name = "created_at",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true,referencedColumnName = "id") // Optional side
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id",nullable = true,referencedColumnName = "id")
    private Payments payments;

}

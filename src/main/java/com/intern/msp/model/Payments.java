package com.intern.msp.model;

import com.intern.msp.Enum.PaymentMethod;
import com.intern.msp.Enum.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.web.ProjectedPayload;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payments")
@Data
public class Payments {
    @Id
    private Long id;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @Min(value = 0, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    // @Enumerated(EnumType.STRING)
    // @Column(name = "payment_status", nullable = false,columnDefinition = "DEFAULT 'PENDING'")
    // private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "is_payment_verified")
    private String isPaymentVerified;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = false)
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Projects projects;

    @OneToOne(mappedBy = "payments", optional = true)
    private RecurringBudgets recurringBudgets;

}

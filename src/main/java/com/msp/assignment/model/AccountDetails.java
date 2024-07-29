package com.msp.assignment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "account_details")
@Data
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String lastName;

    @Column(name = "account_number", nullable = false, columnDefinition = "VARCHAR(30)")
    private String accountNumber;

    @Column(name = "bank_name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String bankName;

    @Column(name = "credit_cardNumber", nullable = false, columnDefinition = "VARCHAR(30)")
    private String creditCardNumber;

    @Column(name = "registered_phoneNumber", nullable = false, columnDefinition = "VARCHAR(30)")
    private String registeredPhoneNumber;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payments payments;


}

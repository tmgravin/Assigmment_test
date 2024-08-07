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

    @Column(name = "first_name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String lastName;

    @Column(name = "account_number", columnDefinition = "VARCHAR(30)", nullable = false)
    private String accountNumber;

    @Column(name = "bank_name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String bankName;

    @Column(name = "credit_cardNumber", columnDefinition = "VARCHAR(30)")
    private String creditCardNumber;

    @Column(name = "registered_phoneNumber", columnDefinition = "VARCHAR(30)", nullable = false)
    private String registeredPhoneNumber;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private Users users;
}

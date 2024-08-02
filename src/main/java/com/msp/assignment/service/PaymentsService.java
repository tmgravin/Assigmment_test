package com.msp.assignment.service;

import com.msp.assignment.model.Payments;

import java.util.Optional;

public interface PaymentsService {
    Payments savePayment(Payments payments);
    Optional<Payments> getPayments(Long id);
}

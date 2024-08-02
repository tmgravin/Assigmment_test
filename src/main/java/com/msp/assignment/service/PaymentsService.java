package com.msp.assignment.service;

import com.msp.assignment.enumerated.PaymentStatus;
import com.msp.assignment.model.Payments;

import java.util.List;

public interface PaymentsService {
    Payments savePayment(Payments payments);
//    List<Payments> getPayments(Long id);
    List<Payments> findByProjectsId(Long projectsId);
}

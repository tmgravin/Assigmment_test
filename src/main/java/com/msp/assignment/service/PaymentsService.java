package com.msp.assignment.service;

import com.msp.assignment.enumerated.PaymentMethod;
import com.msp.assignment.model.Payments;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PaymentsService {
    Payments savePayment(double amount, PaymentMethod paymentMethod, MultipartFile screenshotUrl, Users users, Projects projects);
    Optional<Payments> getPayments(Long id);
}

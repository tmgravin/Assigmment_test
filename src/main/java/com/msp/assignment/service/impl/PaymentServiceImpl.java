package com.msp.assignment.service.impl;

import com.msp.assignment.model.Payments;
import com.msp.assignment.repository.PaymentRepo;
import com.msp.assignment.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentsService {
    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public Payments savePayment(Payments payments) {
        return paymentRepo.save(payments);
    }


    @Override
    public List<Payments> findByProjectsId(Long projectsId) {
        return List.of();
    }
}

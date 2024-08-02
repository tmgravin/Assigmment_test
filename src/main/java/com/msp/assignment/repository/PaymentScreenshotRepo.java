package com.msp.assignment.repository;

import com.msp.assignment.model.PaymentScreenshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentScreenshotRepo extends JpaRepository<PaymentScreenshot, Long> {
}

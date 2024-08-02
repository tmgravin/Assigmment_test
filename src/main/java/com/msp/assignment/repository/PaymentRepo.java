package com.msp.assignment.repository;

import com.msp.assignment.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payments, Long> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payments p WHERE p.projects.id = :projectId")
    double sumPaymentByProjectsId(@Param("projectId") Long projectId);
}

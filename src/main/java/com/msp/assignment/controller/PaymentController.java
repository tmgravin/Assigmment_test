package com.msp.assignment.controller;

import com.msp.assignment.model.Payments;
import com.msp.assignment.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/")
    public ResponseEntity<Payments> addPayments(Payments payments){
        Payments savePayment=paymentsService.savePayment(payments);
        return ResponseEntity.status(HttpStatus.CREATED).body(savePayment);
    }
//
//    @GetMapping("/")
//    public ResponseEntity<Payments> getPayments(@RequestParam(name="id", required = false) Long id){
//
//    }
}

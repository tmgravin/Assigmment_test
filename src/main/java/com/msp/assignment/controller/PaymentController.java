package com.msp.assignment.controller;

import com.msp.assignment.enumerated.PaymentMethod;
import com.msp.assignment.model.Payments;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.Users;
import com.msp.assignment.service.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentsService paymentsService;

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/")
    public ResponseEntity<Payments> addPayments(@RequestParam("amount") double amount,
                                                @RequestParam("paymentMethod") PaymentMethod paymentMethod,
                                                @RequestParam("screenshotUrl") MultipartFile screenshotUrl,
                                                @RequestParam("users") Users users,
                                                @RequestParam("projects") Projects projects) {
        try {
            Payments savePayment = paymentsService.savePayment(amount, paymentMethod, screenshotUrl, users, projects);
            log.info("Payment added successfully" + savePayment.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savePayment);
        } catch (Exception e) {
            e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
//
//    @GetMapping("/")
//    public ResponseEntity<Payments> getPayments(@RequestParam(name="id", required = false) Long id){
//
//    }
}

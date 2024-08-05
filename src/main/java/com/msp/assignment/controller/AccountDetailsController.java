package com.msp.assignment.controller;

import com.msp.assignment.model.AccountDetails;
import com.msp.assignment.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountDetailsController {
    @Autowired
    private AccountDetailsService accountDetailsService;

    @PostMapping("/")
    public ResponseEntity<AccountDetails> addAccountDetails(@RequestBody AccountDetails accountDetails) {
        AccountDetails createdAccountDetails = accountDetailsService.addAccountDetails(accountDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountDetails);
    }

}

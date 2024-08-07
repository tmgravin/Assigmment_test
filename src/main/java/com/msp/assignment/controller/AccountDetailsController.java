package com.msp.assignment.controller;

import com.msp.assignment.exception.ResourceConflictException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.AccountDetails;
import com.msp.assignment.service.AccountDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountDetailsController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private AccountDetailsService accountDetailsService;

    //Api for getting bank details by userId and all bank details
    @GetMapping("/")
    public ResponseEntity<?> getAccountDetails(@RequestParam(name = "userId", required = false) Long userId){
        log.info("Inside getAccountDetails method of AccountDetailsController.");
        try{
            List<AccountDetails> accountDetails = accountDetailsService.getAccountDetails(userId);
                return ResponseEntity.ok(accountDetails);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for posting bank details
    @PostMapping("/")
    public ResponseEntity<?> addAccountDetails(@RequestBody AccountDetails accountDetails) {
        log.info("Inside addAccountDetails method of AccountDetailsController.");
        try {
            AccountDetails createdAccountDetails = accountDetailsService.addAccountDetails(accountDetails);
            return ResponseEntity.ok(createdAccountDetails);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ResourceConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

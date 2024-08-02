package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.PaymentStatus;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.CompletedProject;
import com.msp.assignment.model.Payments;
import com.msp.assignment.model.Projects;
import com.msp.assignment.model.Users;
import com.msp.assignment.repository.CompletedProjectRepo;
import com.msp.assignment.repository.PaymentRepo;
import com.msp.assignment.repository.ProjectRepo;
import com.msp.assignment.repository.UsersRepository;
import com.msp.assignment.service.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentsService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CompletedProjectRepo completedProjectRepo;

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public Payments savePayment(Payments payments) {
        log.info("Inside savePayment method of PaymentServiceImpl");
        try {
            // Retrieve the project associated with the payment. Throw an exception if not found.
            Projects project = projectRepo.findById(payments.getProjects().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project Not Found"));

            // Retrieve the user making the payment. Throw an exception if not found.
            Users user = usersRepository.findById(payments.getUsers().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            // Calculate the total amount paid for the project by summing up all previous payments and the current payment amount.
            double totalPaid = paymentRepo.sumPaymentByProjectsId(payments.getProjects().getId()) + payments.getAmount();

            // Convert the project amount to a double for comparison.
            double projectAmount = Double.parseDouble(project.getProjectAmount());

            // Retrieve the completed project record associated with the project.
            CompletedProject completedProject = completedProjectRepo.findByProjectsId(payments.getProjects().getId());

            // Update the payment status based on the total amount paid compared to the project amount.
            if (totalPaid == projectAmount) {
                completedProject.setPaymentStatus(PaymentStatus.COMPLETED);
            } else if (totalPaid > 0 && totalPaid < projectAmount) {
                completedProject.setPaymentStatus(PaymentStatus.INCOMPLETE);
            } else {
                completedProject.setPaymentStatus(PaymentStatus.PENDING);
            }

            // Save the updated completed project record.
            completedProjectRepo.save(completedProject);

            // Mark the payment as not verified initially.
            payments.setIsPaymentVerified("N");

            // Save the payment record in the database.
            Payments savedPayment = paymentRepo.save(payments);

            // Log the successful payment save operation.
            log.info("Payment saved successfully for project ID: {}", payments.getProjects().getId());

            // Return the saved payment record.
            return savedPayment;

        } catch (ResourceNotFoundException e) {
            // Log the resource not found error and rethrow the exception.
            log.error("Resource not found: ", e);
            throw e;
        } catch (Exception e) {
            // Log any other errors that occur and throw a runtime exception.
            log.error("Error saving payment: ", e);
            throw new RuntimeException("Error saving payment", e);
        }
    }

    @Override
    public Optional<Payments> getPayments(Long id) {
        log.info("Fetching payment with ID: {}", id); // Log the ID for which payment is being fetched

        try {
            // Attempt to retrieve the payment from the repository
            Optional<Payments> payments = paymentRepo.findById(id);

            // Check if the payment is present
            if (payments.isEmpty()) {
                // If not found, log a message and throw a ResourceNotFoundException
                log.warn("Payment not found with ID: {}", id);
                throw new ResourceNotFoundException("Payment not found with ID: " + id);
            }

            // Return the found payment
            return payments;

        } catch (Exception e) {
            // Log any exception that occurs during the retrieval process
            log.error("Error retrieving payment with ID: {} - Exception: {}", id, e.getMessage());

            // Rethrow the exception as a RuntimeException with a message
            throw new RuntimeException("Internal server error while retrieving payment", e);
        }
    }

}
package com.msp.assignment.service.impl;

import com.msp.assignment.enumerated.PaymentMethod;
import com.msp.assignment.enumerated.PaymentStatus;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.*;
import com.msp.assignment.repository.*;
import com.msp.assignment.service.PaymentsService;
import com.msp.assignment.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private PaymentScreenshotRepo paymentScreenshotRepo;

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public Payments savePayment(double amount, PaymentMethod paymentMethod, MultipartFile screenshotUrl, Users users, Projects projects, Long id) {
        log.info("Inside savePayment method of PaymentServiceImpl");

        try {
            // Create and save the payment record
            Payments payments = new Payments();

            PaymentScreenshot paymentScreenshot = new PaymentScreenshot();

            if (id != null) {
                payments = paymentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment Not Found with id:" + id));
                paymentScreenshot = paymentScreenshotRepo.findByPaymentsId(id).orElseThrow(() -> new ResourceNotFoundException("Payment ScreenShot Not Found with id:" + id));
            }

            // Retrieve the project associated with the payment. Throw an exception if not found.
            Projects project = projectRepo.findById(projects.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project Not Found"));

            // Retrieve the user making the payment. Throw an exception if not found.
            Users user = usersRepository.findById(users.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));


            payments.setAmount(amount);
            payments.setPaymentMethod(paymentMethod);
            payments.setUsers(user);
            payments.setProjects(project);
            payments.setIsPaymentVerified("N");  // Initially not verified

            // Save the payment record
            Payments savedPayment = paymentRepo.save(payments);

            // Handle screenshot URL if provided
            if (screenshotUrl != null && !screenshotUrl.isEmpty()) {
                String filePath = fileUtils.generateFileName(screenshotUrl);
                fileUtils.saveFile(screenshotUrl, filePath);

                paymentScreenshot.setScreenshotUrl(filePath);
                paymentScreenshot.setPayments(savedPayment);

                // Save the screenshot record
                paymentScreenshotRepo.save(paymentScreenshot);
                log.info("Screenshot uploaded and saved to path: {}", filePath);
            }

//            // Calculate the total amount paid for the project
//            double totalPaid = paymentRepo.sumPaymentByProjectsId(projects.getId()) + amount;
//
//            // Convert the project amount to a double for comparison
//            double projectAmount = Double.parseDouble(project.getProjectAmount());
//
//            // Update the payment status based on the total amount paid compared to the project amount
//            if (totalPaid == projectAmount) {
//                project.setPaymentStatus(PaymentStatus.COMPLETED);
//            } else if (totalPaid > 0 && totalPaid < projectAmount) {
//                project.setPaymentStatus(PaymentStatus.INCOMPLETE);
//            } else {
//                project.setPaymentStatus(PaymentStatus.PENDING);
//            }

            // Calculate the total amount paid for the project by summing up all previous payments and the current payment amount.
            double totalPaid = paymentRepo.sumPaymentByProjectsId(projects.getId()) + amount;

            // Convert the project amount to a double for comparison.
            double projectAmount = Double.parseDouble(project.getProjectAmount());

            // Update the payment status based on the total amount paid compared to the project amount.
            PaymentStatus paymentStatus;
            if (totalPaid >= projectAmount) {
                paymentStatus = PaymentStatus.COMPLETED;
            } else if (totalPaid > 0) {
                paymentStatus = PaymentStatus.INCOMPLETE;
            } else {
                paymentStatus = PaymentStatus.PENDING;
            }
            project.setPaymentStatus(paymentStatus);


            // Save the updated project record
            projectRepo.save(project);

            // Log the successful payment save operation
            log.info("Payment saved successfully for project ID: {}", savedPayment.getProjects().getId());

            // Return the saved payment record
            return savedPayment;

        } catch (IOException e) {
            // Log and throw the file handling error
            log.error("Error handling file upload", e);
            throw new RuntimeException("Error handling file upload", e);
        } catch (ResourceNotFoundException e) {
            // Log and rethrow the resource not found error
            log.error("Resource not found: ", e);
            throw e;
        } catch (Exception e) {
            // Log and throw any other errors
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
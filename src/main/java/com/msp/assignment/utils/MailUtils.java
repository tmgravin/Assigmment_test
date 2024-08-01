package com.msp.assignment.utils;

import com.msp.assignment.exception.EmailRelatedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class MailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    //Mail configuration for user verification token
    public void emailVerificationToken(String toEmail, String recipientName, String verificationLink) throws MessagingException, IOException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String subject = "Email verification for MSP Assignment login.";

            ClassPathResource htmlFile = new ClassPathResource("templates/Assignment_verifyEmail.html");
            String htmlContent = StreamUtils.copyToString(htmlFile.getInputStream(), StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("[link]", verificationLink);
            htmlContent = htmlContent.replace("[name]", recipientName);

            helper.setFrom("MSP Academy <mspacademy0@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (EmailRelatedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }

    }

    //Mail configuration for forget password code verification
    public void forgetPasswordVerificationCode(String toEmail, String recipientName, int verificationCode) throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String subject = "Forget password verification code.";

            //Load html templates
            ClassPathResource htmlFile = new ClassPathResource("templates/Assignment_verifyCode.html");
            String htmlContent = StreamUtils.copyToString(htmlFile.getInputStream(), StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("[code]", String.valueOf(verificationCode));
            htmlContent = htmlContent.replace("[name]", recipientName);

            helper.setFrom("MSP Academy <mspacademy0@gmail.com>");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (EmailRelatedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }

    }
}

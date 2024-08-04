package com.msp.assignment.config;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>
{
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event)
    {
//        String email = event.getAuthentication().getName();
//        // Perform custom logic here, such as logging or storing user details
//        System.out.println("User logged in: " + email);
    }
}

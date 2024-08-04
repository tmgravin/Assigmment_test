package com.msp.assignment.config;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoutSuccessEventListener implements ApplicationListener<LogoutSuccessEvent>
{
    @Override
    public void onApplicationEvent(LogoutSuccessEvent event)
    {
        String email = event.getAuthentication().getName();
        // Perform custom logic here, such as logging or removing user details
        System.out.println("(LogoutSuccessEventListener) User logged out: " + email);
    }
}

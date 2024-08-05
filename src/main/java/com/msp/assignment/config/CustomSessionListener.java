package com.msp.assignment.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class CustomSessionListener implements HttpSessionListener
{
	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
//    	System.out.println("Session created event CustomSessionListener : " + se.getSession().getAttribute("email"));
//    	System.out.println("Session created for ID: " + se.getSession().getId());
	}

    @Override
    public void sessionDestroyed(HttpSessionEvent se)
    {
    	// Perform custom logic on session timeout, such as logging
//    	String email = (String) se.getSession().getAttribute("email");
//    	System.out.println("Session destroyed event CustomSessionListener (session timeout) : " + email);
//    	System.out.println("here in sessionDestroyed : " + se.getSession().getId());
	}
}

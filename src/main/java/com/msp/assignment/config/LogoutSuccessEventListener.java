package com.msp.assignment.config;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import com.msp.assignment.model.SessionUsers;
import com.msp.assignment.repository.SessionUserRepo;

@Component
public class LogoutSuccessEventListener implements ApplicationListener<LogoutSuccessEvent>
{
	@Autowired
	private SessionUserRepo sessionUserRepo;

	@Override
	public void onApplicationEvent(LogoutSuccessEvent event)
	{
		// Perform custom logic here, such as logging or removing user details
//		String email = event.getAuthentication().getName();
//		System.out.println("(LogoutSuccessEventListener) User logged out: " + email);
		// save session to database
		SessionUsers session = sessionUserRepo.getByUserEmail(event.getAuthentication().getName()).get(0);
		session.setLastLogout(Timestamp.from(Instant.now()));
		sessionUserRepo.save(session);
    }
}

package com.msp.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.msp.assignment.model.Users;
import com.msp.assignment.service.UsersService;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UsersService userService;
	
	@Override
	// here email is used as username
	public UserDetails loadUserByUsername(String email)
	{
		try {
			Users user = userService.getUserByEmail(email); //getUser(username);
			if(user != null && user.getIsEmailVerified() == 'N'){
				throw new InternalAuthenticationServiceException("User is not verified, please verify your email before login.");
			} else if (user != null && user.getId() != null) {
				LoginUtils.addLoggedInUser(email, user);
				return User.withUsername(user.getEmail())
						.password(user.getPassword())
						.roles(user.getUserType().toString())
						.build();
			}
			else
				throw new UsernameNotFoundException("Email not found");
		} catch (InternalAuthenticationServiceException | UsernameNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Internal server error:" + e.getMessage(), e);
		}
	}
}

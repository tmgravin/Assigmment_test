package com.msp.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		System.out.println("Inside loadUserByUsername of CustomUserDetailsService: " + email);
		Users user = userService.getUserByEmail(email); //getUser(username);
		if(user != null && user.getId() != null)
		{
//			LoginUtils.addLoggedInUser(email, user);
			
			// Log or manage user session information
	        // For example, add the user to LoginUtils (though this is not typical for this method)
	        // Use a unique identifier such as username or user ID
	        String sessionId = getSessionIdForUser(email); // username
	        LoginUtils.addUser(sessionId, user);
	        
			return User.withUsername(user.getEmail())
					.password(user.getPassword())
					.roles(user.getUserType().toString())
					.build();
		}
		else
			throw new UsernameNotFoundException("User not found");
	}
	
	private String getSessionIdForUser(String username)
	{
        // Implement logic to get the session ID for a given user
        // This may involve fetching it from the current session context
        return "mock-session-id"; // Replace with actual session ID retrieval logic
    }
}

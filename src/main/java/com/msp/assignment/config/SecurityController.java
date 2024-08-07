package com.msp.assignment.config;

import com.msp.assignment.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.msp.assignment.model.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/security")
//@CrossOrigin
public class SecurityController
{
	@Autowired
    private AuthenticationManager authenticationManager;
    
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users authRequest, HttpServletRequest request, HttpServletResponse response)
    {
    	
    	log.info("Inside login method of SecurityController: " + authRequest.getEmail());
        try
        {
        	Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            
//            Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

//            return ResponseEntity.ok().body(jwtUtil.generateToken(authRequest.getEmail()));
            return ResponseEntity.ok().body(LoginUtils.geUserInfo());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    }
    
    @GetMapping("/user-login")
	public ResponseEntity<?> userLogin()
	{
    	log.info("Inside user-login method of SecurityController: ");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login before accessing other page.");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> out()
	{
		log.info("Inside logout method of SecurityController: ");
		return ResponseEntity.ok().body("LOGOUT");
	}

	@GetMapping("/users")
	public ResponseEntity<?> user()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Inside user method of SecurityController: " + authentication.getName());
		return ResponseEntity.ok().body(LoginUtils.geUserInfo());
	}
}

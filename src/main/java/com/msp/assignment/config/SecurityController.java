package com.msp.assignment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msp.assignment.model.Users;

@RestController
@RequestMapping("/api/security")
public class SecurityController
{
	@Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private JwtUtil jwtUtil;
    
    private static final Logger log = LoggerFactory.getLogger(SecurityController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users authRequest)
    {
    	log.info("INSIDE LOGIN METHOD OF SecurityController: " + authRequest.getEmail());
        try
        {
        	Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return ResponseEntity.ok().body(jwtUtil.generateToken(authRequest.getEmail()));
            return ResponseEntity.ok().body(LoginUtils.geUserInfo());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
        }
    }
    
    @GetMapping("/user-login")
	public ResponseEntity<?> userLogin()
	{
    	log.info("INSIDE USERLOGIN METHOD OF SecurityController: ");
		return ResponseEntity.unprocessableEntity().body("GO TO LOGIN PAGE");
	}
	
	@GetMapping("/homepage")
	public ResponseEntity<?> homepage()
	{
		log.info("INSIDE HOMEPAGE METHOD OF SecurityController: ");
		return ResponseEntity.ok().body("GO TO HOMEPAGE");
	}
	
	@GetMapping("/out")
	public ResponseEntity<?> out()
	{
		log.info("INSIDE OUT METHOD OF SecurityController: ");
		return ResponseEntity.ok().body("LOGOUT");
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> user()
	{
		log.info("INSIDE USER METHOD OF SecurityController: ");
//		return ResponseEntity.ok().body(LoginUtils.geUserInfo());
		return null;
	}
}

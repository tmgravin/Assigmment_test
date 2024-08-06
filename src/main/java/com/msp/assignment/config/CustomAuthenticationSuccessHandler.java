package com.msp.assignment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.msp.assignment.model.SessionUsers;
import com.msp.assignment.repository.SessionUserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	@Autowired
	private SessionUserRepo sessionUserRepo;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
//        System.out.println("(CustomAuthenticationSuccessHandler) Login successful, session ID: " + request.getSession().getId());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
//        Set the SecurityContext with the authentication information
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        
        // save session to database
        SessionUsers session = new SessionUsers();
        session.setUserId(LoginUtils.geUserInfo().getId());
        session.setSessionId(request.getSession().getId());
        session.setLastLogin(Timestamp.from(Instant.now()));
        session.setLastLogout(null);
        sessionUserRepo.save(session);
        

        // Write the response as JSON
//        response.getWriter().write(LoginUtils.geUserInfo().getId().toString());
        
        // Redirect to the default success URL
        response.sendRedirect("/api/security/users");
        
        response.getWriter().flush();
    }
}

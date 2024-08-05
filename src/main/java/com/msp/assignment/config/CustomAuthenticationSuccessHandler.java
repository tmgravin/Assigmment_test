package com.msp.assignment.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
//	@Autowired
//	private UsersService userService;
	
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

        // Write the response as JSON
//        response.getWriter().write(LoginUtils.geUserInfo().getId().toString());
        
        // Redirect to the default success URL
        response.sendRedirect("/api/security/user");
        
        response.getWriter().flush();
    }
}

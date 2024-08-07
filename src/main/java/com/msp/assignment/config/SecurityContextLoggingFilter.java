package com.msp.assignment.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityContextLoggingFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null)
//        {
//        	System.out.println("Authenticated user: " + authentication.getName());
//        }
//        else
//        {
//        	System.out.println("No authentication in security context");
//        }
//        System.out.println("Session ID: " + request.getSession().getId());
        filterChain.doFilter(request, response);
    }
}

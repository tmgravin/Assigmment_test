package com.msp.assignment.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        System.out.println("(CustomAuthenticationSuccessHandler) Login successful, session ID: " + request.getSession().getId());
//        response.sendRedirect("/api/security/login");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the response as JSON
//        response.getWriter().write(LoginUtils.geUserInfo().getId().toString());
        response.getWriter().flush();
    }
}

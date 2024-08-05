package com.msp.assignment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomJsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomJsonAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager)
    {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException
    {
        Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), HashMap.class);
        String username = credentials.get("email");
        String password = credentials.get("password");
//        System.out.println("Inside attemptAuthentication " + username + " :: " + password);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

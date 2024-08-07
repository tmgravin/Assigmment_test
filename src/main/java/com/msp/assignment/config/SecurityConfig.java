package com.msp.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)// throws Exception
    {
        try {
            http
                    .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity, you might want to enable it in production
                    .authorizeHttpRequests(authorize -> authorize
                                    .requestMatchers("/api/security/**").permitAll()
                                    .requestMatchers("/api/users/verifyEmail/**", "/api/users/verifyResetCode/**",
                                            "/api/users/resetPassword/**", "/api/users/requestEmailToken/**",
                                            "/api/users/forgetPassword/**", "/api/users/signup/**", "/api/users/verifyEmail").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/projects/**").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/api/featureImages/**").permitAll()

                                     // All other requests require authentication
                                    .anyRequest().authenticated()
                    )
                    .formLogin(formLogin -> formLogin
                                    .loginPage("/api/security/user-login") // Custom login page
                                    .loginProcessingUrl("/api/security/login")
//                                    .usernameParameter("email")
//                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/api/security/users", true)
                                    .successHandler(customAuthenticationSuccessHandler1())
                                    .failureUrl("/api/security/user-login")
                                    .permitAll()
                    )
                    .addFilterAt(customJsonAuthenticationFilter(authenticationManager1(http.getSharedObject(AuthenticationConfiguration.class))),
                            UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(securityContextLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
                    .logout(logout -> logout
                                    .logoutUrl("/api/security/logout") // url for log out
                                    .deleteCookies("JSESSIONID")
//                                    .logoutSuccessUrl("/api/security/user-login") // Redirect to __ on success
                                    .permitAll() // Allow everyone to logout
                    )
                    .sessionManagement(management -> management
                                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                    .sessionFixation().newSession() // Creates a new session after login
                                    .maximumSessions(1)
                                    .maxSessionsPreventsLogin(true)
                                    .expiredUrl("/api/security/user-login")
//                            .invalidSessionUrl("/api/security/user-login") // Redirects to Login page if the session is invalid
                    )
                    .rememberMe(me -> me
                            .key("uniqueAndSecret") // Set a unique key for remember-me functionality
                            .tokenValiditySeconds(86400)); // Set token validity in seconds (e.g., 1 day)
            ;
            return http.build();
        } catch (Exception e) {
            System.out.println("ERROR IN SecurityConfig: " + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public CustomJsonAuthenticationFilter customJsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomJsonAuthenticationFilter filter = new CustomJsonAuthenticationFilter("/api/security/login", authenticationManager);
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager1(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler1() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityContextLoggingFilter securityContextLoggingFilter() {
        return new SecurityContextLoggingFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}


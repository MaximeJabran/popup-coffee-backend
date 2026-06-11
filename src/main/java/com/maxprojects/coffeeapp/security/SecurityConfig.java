package com.maxprojects.coffeeapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for REST API usage
                .csrf(csrf -> csrf.disable())

                // Allow CORS (frontend on Vercel)
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(auth -> auth

                        // ⭐ PUBLIC ENDPOINTS
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/error").permitAll()

                        // Public membership creation
                        .requestMatchers(HttpMethod.POST, "/membership").permitAll()

                        // Public registration creation
                        .requestMatchers(HttpMethod.POST, "/registrations").permitAll()

                        // Public event info
                        .requestMatchers("/events/next").permitAll()
                        .requestMatchers("/events/upcoming").permitAll()

                        // ⭐ ADMIN‑ONLY ENDPOINTS
                        .requestMatchers("/registrations/admin/**").authenticated()
                        .requestMatchers("/membership/admin/**").authenticated()
                        .requestMatchers("/events/admin/**").authenticated()
                        .requestMatchers("/admin/**").authenticated()

                        // ⭐ ANY OTHER REQUEST
                        .anyRequest().permitAll()
                )

                // Disable default login form
                .formLogin(form -> form.disable())

                // Disable HTTP Basic popup
                .httpBasic(basic -> basic.disable())

                // Logout endpoint
                .logout(logout -> logout.logoutUrl("/auth/logout"));

        return http.build();
    }
}

package com.maxprojects.coffeeapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // API → disable CSRF
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(
                                "/api/membership",
                                "/api/membership/verify",
                                "/api/membership/register",
                                "/registrations",
                                "/error"
                        ).permitAll()

                        // Admin-only endpoints
                        .requestMatchers(
                                "/api/membership/**",
                                "/api/memberships/**",
                                "/api/registrations/**",
                                "/admin/**"
                        ).authenticated()

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable()) // We use our own login endpoint
                .httpBasic(basic -> basic.disable()) // No basic auth
                .logout(logout -> logout.logoutUrl("/auth/logout"));

        return http.build();
    }
}

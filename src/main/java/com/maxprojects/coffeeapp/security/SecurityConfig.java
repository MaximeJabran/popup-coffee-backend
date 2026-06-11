package com.maxprojects.coffeeapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AdminSessionFilter adminSessionFilter;

    public SecurityConfig(AdminSessionFilter adminSessionFilter) {
        this.adminSessionFilter = adminSessionFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for REST API usage
                .csrf(csrf -> csrf.disable())

                // Allow CORS (frontend on Vercel)
                .cors(Customizer.withDefaults())

                // ⭐ Add your custom admin session filter BEFORE authentication
                .addFilterBefore(adminSessionFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/membership").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registrations").permitAll()
                        .requestMatchers("/events/next").permitAll()
                        .requestMatchers("/events/upcoming").permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/registrations/admin/**").authenticated()
                        .requestMatchers("/membership/admin/**").authenticated()
                        .requestMatchers("/events/admin/**").authenticated()
                        .requestMatchers("/admin/**").authenticated()

                        // Any other request
                        .anyRequest().permitAll()
                )

                // Disable default login form and HTTP Basic
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // Logout endpoint
                .logout(logout -> logout.logoutUrl("/auth/logout"));

        return http.build();
    }
}

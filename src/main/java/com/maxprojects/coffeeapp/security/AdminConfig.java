package com.maxprojects.coffeeapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;


    public String getUsername() {
        return adminUsername;
    }

    public String getPassword() {
        return adminPassword;
    }
}

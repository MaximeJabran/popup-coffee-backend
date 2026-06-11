package com.maxprojects.coffeeapp.auth;

import com.maxprojects.coffeeapp.security.AdminConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletRequest request) {

        // ⭐ THIS IS WHERE YOU INSERT THE CHECK
        if (loginRequest.getUsername().equals(adminUsername) &&
                loginRequest.getPassword().equals(adminPassword)) {

            // Create session
            HttpSession session = request.getSession(true);
            session.setAttribute("ADMIN", true);


            return ResponseEntity.ok().body(Map.of("status", "success"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("status", "invalid_credentials"));
    }


    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && Boolean.TRUE.equals(session.getAttribute("ADMIN"))) {
            return ResponseEntity.ok().body("Authenticated");
        }

        return ResponseEntity.status(401).body("Not authenticated");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().body("Logged out");
    }
}

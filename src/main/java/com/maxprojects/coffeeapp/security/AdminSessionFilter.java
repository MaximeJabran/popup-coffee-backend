package com.maxprojects.coffeeapp.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AdminSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();

        // ⭐ Only protect admin-prefixed endpoints
        boolean isAdminEndpoint =
                path.startsWith("/membership/admin") ||
                path.startsWith("/registrations/admin") ||
                path.startsWith("/events/admin") ||
                path.startsWith("/admin");

        // If not an admin endpoint → skip filter
        if (!isAdminEndpoint) {
            chain.doFilter(req, res);
            return;
        }

        // Check session for ADMIN flag
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("ADMIN") == null) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Admin session required");
            return;
        }

        // Mark request as authenticated admin
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }
}

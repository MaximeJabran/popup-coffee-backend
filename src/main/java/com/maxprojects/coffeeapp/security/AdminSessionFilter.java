package com.maxprojects.coffeeapp.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AdminSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();

        boolean isAdminEndpoint =
                path.startsWith("/api/membership") ||
                        path.startsWith("/api/memberships") ||
                        path.startsWith("/api/registrations") ||
                        path.startsWith("/admin");

        if (!isAdminEndpoint) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("ADMIN") == null) {
            response.setStatus(401);
            return;
        }

        chain.doFilter(req, res);
    }
}

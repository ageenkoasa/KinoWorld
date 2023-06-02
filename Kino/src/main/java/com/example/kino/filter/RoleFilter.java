package com.example.kino.filter;

import com.example.kino.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
public class RoleFilter implements Filter, jakarta.servlet.Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        User user = (User) httpRequest.getSession().getAttribute("user");
        if (user != null) {
            if (user.hasRole("ADMIN")) {
                chain.doFilter(request, response);
                return;
            }
        }

        httpResponse.sendRedirect("/");

        chain.doFilter(request, response);
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}

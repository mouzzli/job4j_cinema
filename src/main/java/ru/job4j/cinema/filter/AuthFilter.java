package ru.job4j.cinema.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthFilter extends AbstractFilter implements Filter {
    private static final String LOGIN_FORM = "loginForm";
    private static final String REGISTRATION_FORM = "registrationForm";
    private static final String REGISTRATION = "registration";
    private static final String LOGIN = "login";
    private static final Set<String> FILTER_VALUES = new HashSet<>(Arrays.asList(
            LOGIN_FORM,
            REGISTRATION_FORM,
            REGISTRATION,
            LOGIN));

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        if (filter(req.getRequestURI(), FILTER_VALUES)) {
            filterChain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "loginForm");
            return;
        }
        filterChain.doFilter(req, res);
    }
}

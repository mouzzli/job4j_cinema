package ru.job4j.cinema.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {
    private static final String ERROR = "/error/404";
    private static final String FILM_LIBRARY = "/filmLibrary";
    private static final String REGISTER = "/users/register";
    private static final String LOGIN = "/users/login";
    private static final String FILE = "/files/";
    private static final String SCHEDULE = "/schedule";
    private static final String INDEX = "/index";
    private static final String FILM_SESSION = "/filmSession/";

    private static final Set<String> FILTER_VALUES = new HashSet<>(Arrays.asList(
            ERROR,
            FILM_LIBRARY,
            REGISTER,
            LOGIN,
            FILE,
            SCHEDULE,
            INDEX,
            FILM_SESSION));

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        var userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            var loginPageUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isAlwaysPermitted(String uri) {
        return AuthorizationFilter.FILTER_VALUES.stream().anyMatch(uri::startsWith);
    }
}

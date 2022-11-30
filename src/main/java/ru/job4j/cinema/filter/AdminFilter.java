package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import ru.job4j.cinema.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AdminFilter extends AbstractFilter implements Filter {
    private static final String ADMIN = "admin";
    private static final String ADD_FILM = "addFilm";
    private static final String DELETE_FILM = "deleteFilm";
    private static final String ADD_SESSION = "addSession";
    private static final String DELETE_SESSION = "deleteSession";
    private static final Set<String> FILTER_VALUES = new HashSet<>(Arrays.asList(
            ADMIN,
            ADD_FILM,
            DELETE_FILM,
            ADD_SESSION,
            DELETE_SESSION));

    /**
     * Если у пользователя есть права администратора даём доступ, иначе перенаправляем на главную
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        User user = (User) req.getSession().getAttribute("user");
        if (filter(uri, FILTER_VALUES)) {
            if (user == null || !user.isAdmin()) {
                res.sendRedirect(req.getContextPath() + "/index");
                return;
            }
        }
        filterChain.doFilter(req, res);
    }
}

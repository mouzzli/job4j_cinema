package ru.job4j.cinema.controller;


import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.JDBCUserService;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private final UserService userService;
    private final UserController userController;

    public UserControllerTest() {
        userService = mock(JDBCUserService.class);
        userController = new UserController(userService);
    }

    /**
     * Добавляет флаг fail в модель и возвращает форму регистрации
     */
    @Test
    public void registrationForm() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        Boolean fail = true;
        String page = userController.registrationForm(session, model, fail);
        verify(model).addAttribute("fail", fail);
        assertThat(page).isEqualTo("registrationForm");
    }

    /**
     * Добавляет юзера и возвращает список фильмов
     */
    @Test
    public void registration() {
        User user = new User(1, "name", "email", "phone", "password");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(userService.save(user)).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);
        String page = userController.registration(user, request);
        verify(session).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/index");
    }

    /**
     * Возвращает форму регистрации если сервис не смог добавить юзера
     */
    @Test
    public void registrationFail() {
        User user = new User(1, "name", "email", "phone", "password");
        when(userService.save(user)).thenReturn(Optional.empty());
        HttpServletRequest request = mock(HttpServletRequest.class);
        String page = userController.registration(user, request);
        assertThat(page).isEqualTo("redirect:/registrationForm?fail=true");
    }

    /**
     *  Авторизует пользователя, добавляет данные пользователя в сессию и возвращает форму списка фильмов
     */
    @Test
    public void login() {
        User user = new User(1, "name", "email", "phone", "password");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);
        String page = userController.login(user, request);
        verify(session).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/index");
    }

    /**
     * Возвращает форму авторизации если данный пользователь отсутствует
     */
    @Test
    public void loginFail() {
        User user = new User(1, "name", "email", "phone", "password");
        when(userService.save(user)).thenReturn(Optional.empty());
        HttpServletRequest request = mock(HttpServletRequest.class);
        String page = userController.login(user, request);
        assertThat(page).isEqualTo("redirect:/loginForm?fail=true");
    }

    /**
     * Добавляет флаг fail в модель и возвращает форму регистрации
     */
    @Test
    public void loginForm() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        Boolean fail = true;
        String page = userController.loginForm(session, model, fail);
        verify(model).addAttribute("fail", fail);
        assertThat(page).isEqualTo("loginForm");
    }

    /**
     * Сбрасывает сессию пользователя и возвращает форму авторизации
     */
    @Test
    public void logout() {
        HttpSession session = mock(HttpSession.class);
        String page = userController.logout(session);
        verify(session).invalidate();
        assertThat(page).isEqualTo("redirect:/loginForm");
    }
}
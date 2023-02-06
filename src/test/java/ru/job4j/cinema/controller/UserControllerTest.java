package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void userControllerInit() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void getRegistrationForm() {
        String page = userController.getRegistrationForm();
        assertThat(page).isEqualTo("registrationForm");
    }

    @Test
    public void registerWhenEmptyUser() {
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        User user = Mockito.mock(User.class);
        Mockito.when(userService.save(user)).thenThrow(new IllegalArgumentException("Пользователь с такой почтой уже существует"));
        String page = userController.register(model, user, request);
        Mockito.verify(model).addAttribute("message", "Пользователь с такой почтой уже существует");
        assertThat(page).isEqualTo("errors/404");
    }

    @Test
    public void register() {
        HttpSession session = Mockito.mock(HttpSession.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        User user = Mockito.mock(User.class);
        Mockito.when(userService.save(user)).thenReturn(user);
        Mockito.when(request.getSession()).thenReturn(session);
        String page = userController.register(model, user, request);
        Mockito.verify(session).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/index");
    }

    @Test
    public void getLoginForm() {
        String page = userController.getLoginForm();
        assertThat(page).isEqualTo("loginForm");
    }

    @Test
    public  void loginUserWhenEmptyUser() {
        User user = Mockito.mock(User.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenThrow(new IllegalArgumentException("Пользователь с такой почтой или паролем не найден"));
        String page = userController.loginUser(model, user, request);
        Mockito.verify(model).addAttribute("error", "Пользователь с такой почтой или паролем не найден");
        assertThat(page).isEqualTo("loginForm");
    }

    @Test
    public void login() {
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = Mockito.mock(User.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);
        Mockito.when(request.getSession()).thenReturn(session);
        String page = userController.loginUser(model, user, request);
        Mockito.verify(session).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/index");
    }

    @Test
    void logout() {
        HttpSession session = Mockito.mock(HttpSession.class);
        String page = userController.logout(session);
        Mockito.verify(session).invalidate();
        assertThat(page).isEqualTo("redirect:/users/login");
    }
}
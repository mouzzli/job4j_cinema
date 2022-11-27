package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.cinema.util.Session.setSession;

@Controller
public class UserController {
    /**
     * сервис для работы с юзером
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Форма регистрации
     *
     * @param session - сессия пользователя
     * @param model   - модель данных
     * @param fail    - необязательный флаг, указывающий на ошибку регистрации
     * @return форма регистрации
     */
    @GetMapping("/registrationForm")
    public String registrationForm(HttpSession session, Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        setSession(model, session);
        model.addAttribute("fail", fail != null);
        return "registrationForm";
    }

    /**
     * Регистрация пользователя
     *
     * @param user    - модель данных пользователя
     * @param request - HttpServletRequest для получения данных для авторизации
     * @return страницу со списком фильмов если регистрация прошла успешно,
     * в противном случае форму регистрации
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            return "redirect:/registrationForm?fail=true";
        }
        request.getSession().setAttribute("user", savedUser.get());
        return "redirect:/index";
    }

    /**
     * Авторизация пользователя
     *
     * @param user    - модель данных пользователя
     * @param request - HttpServletRequest для получения данных для авторизации
     * @return страница со списком фильмов если авторизация прошла успешно,
     * в противном случае форму авторизации
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> updatedUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (updatedUser.isEmpty()) {
            return "redirect:/loginForm?fail=true";
        }
        request.getSession().setAttribute("user", updatedUser.get());
        return "redirect:/index";
    }

    /**
     * Форма авторизации
     *
     * @param session - сессия пользователя
     * @param model   - модель данных
     * @param fail    - необязательный флаг, указывающий на ошибку авторизации
     * @return форма авторизации
     */
    @GetMapping("/loginForm")
    public String loginForm(HttpSession session, Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        setSession(model, session);
        model.addAttribute("fail", fail != null);
        return "loginForm";
    }

    /**
     * Cброс пользовательской сессии
     *
     * @param session - сессия пользователя
     * @return форма авторизации
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginForm";
    }
}

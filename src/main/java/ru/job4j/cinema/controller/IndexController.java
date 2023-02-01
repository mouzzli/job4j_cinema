package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Session.setSession(model, session);
        return "index";
    }
}

package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpSession;

@Controller
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/filmLibrary")
    public String filmLibrary(Model model, HttpSession session) {
        model.addAttribute("films", filmService.findAll());
        Session.setSession(model, session);
        return "filmLibrary";
    }
}

package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.service.FilmSessionService;

import java.util.NoSuchElementException;

@Controller
public class SessionController {
    private final FilmSessionService filmSessionService;

    public SessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/schedule")
    public String sessionSchedule(Model model) {
        try {
            model.addAttribute("filmSessions", filmSessionService.findAll());
            return "schedule";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/filmSession/{id}")
    public String filmSession(Model model, @PathVariable int id) {
        try {
            model.addAttribute("filmSession", filmSessionService.findById(id));
            return "filmSession";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}

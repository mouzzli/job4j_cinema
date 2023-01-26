package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.FilmSessionService;

@Controller
public class SessionController {
    private final FilmSessionService filmSessionService;

    public SessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/schedule")
    public String sessionSchedule(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "schedule";
    }
}

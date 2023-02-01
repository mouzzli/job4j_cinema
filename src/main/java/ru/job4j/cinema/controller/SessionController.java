package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.util.Session;

import javax.servlet.http.HttpSession;

@Controller
public class SessionController {
    private final FilmSessionService filmSessionService;

    public SessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/schedule")
    public String sessionSchedule(Model model, HttpSession session) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        Session.setSession(model, session);
        return "schedule";
    }

    @GetMapping("/filmSession/{id}")
    public String filmSession(Model model, @PathVariable int id, HttpSession session) {
        model.addAttribute("filmSession", filmSessionService.findById(id));
        Session.setSession(model, session);
        return "bookTicket";
    }
}

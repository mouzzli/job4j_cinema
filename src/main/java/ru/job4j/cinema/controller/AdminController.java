package ru.job4j.cinema.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

import static ru.job4j.cinema.util.Session.setSession;

@Controller
public class AdminController {
    private final FilmService filmService;
    private final SessionService sessionService;

    public AdminController(FilmService filmService, SessionService sessionService) {
        this.filmService = filmService;
        this.sessionService = sessionService;
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session, @ModelAttribute User user) {
        setSession(model, session);
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("sessions", sessionService.findAll());
        return "adminPanel";
    }

    @PostMapping("/addFilm")
    public String addFilm(@ModelAttribute Film film, @RequestParam("file") MultipartFile file) throws IOException {
        film.setPoster(file.getBytes());
        filmService.save(film);
        return "redirect:/admin";
    }

    @PostMapping("/deleteFilm")
    public String deleteFilm(@RequestParam("film.id") int id) {
        filmService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/addSession")
    private String addSession(
            @ModelAttribute Session session,
            @RequestParam("date_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime
    ) {
        session.setDateTime(dateTime);
        sessionService.save(session);
        return "redirect:/admin";
    }

    @PostMapping("/deleteSession")
    private String deleteSession(@RequestParam("session_id") int sessionId) {
        sessionService.deleteById(sessionId);
        return "redirect:/admin";
    }
}

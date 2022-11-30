package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;

import javax.servlet.http.HttpSession;

import java.io.IOException;

import static ru.job4j.cinema.util.Session.setSession;

@Controller
public class AdminController {
    private final FilmService filmService;

    public AdminController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session, @ModelAttribute User user) {
        setSession(model, session);
        model.addAttribute("films", filmService.findAll());
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
}

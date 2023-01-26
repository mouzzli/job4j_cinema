package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.FilmService;

@Controller

public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/filmLibrary")
    public String filmLibrary(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "filmLibrary";
    }
}

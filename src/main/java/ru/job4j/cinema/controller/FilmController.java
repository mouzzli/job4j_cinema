package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmController {

    @GetMapping("/filmLibrary")
    public String filmLibrary() {
        return "filmLibrary";
    }
}

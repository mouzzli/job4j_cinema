package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.FilmService;

import java.util.Optional;

@Controller
public class FilmController {
    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("film", service.findAll());
        return "/index";
    }

    @GetMapping("/poster/{filmId}")
    public ResponseEntity<Resource> downloadPoster(@PathVariable("filmId") Integer filmId) {
        Optional<Film> film = service.findById(filmId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.get().getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(film.get().getPoster()));
    }
}

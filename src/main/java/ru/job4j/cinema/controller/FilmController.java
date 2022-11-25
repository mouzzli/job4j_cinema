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

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.cinema.util.Session.setSession;
@Controller
public class FilmController {
    /**
     * сервис для работы с фильмами
     */
    private final FilmService filmService;

    public FilmController(FilmService service) {
        this.filmService = service;
    }

    /**
     * Возвращает страницу со списком фильмов
     * @param session - сессия пользователя
     * @param model - модель данных
     * @return страница со списком фильмов
     */
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        setSession(model, session);
        model.addAttribute("films", filmService.findAll());
        return "/index";
    }

    /**
     * Возвращает постер по id
     * @param filmId - id постера
     * @return  постер по id
     */
    @GetMapping("/poster/{filmId}")
    public ResponseEntity<Resource> downloadPoster(@PathVariable("filmId") Integer filmId) {
        Optional<Film> film = filmService.findById(filmId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.get().getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(film.get().getPoster()));
    }
}

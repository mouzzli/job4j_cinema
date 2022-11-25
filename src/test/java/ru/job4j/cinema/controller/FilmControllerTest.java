package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.JDBCFilmService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

class FilmControllerTest {

    private FilmController filmController;
    private JDBCFilmService service;

    @BeforeEach
    public void initFilmController() {
        service = mock(JDBCFilmService.class);
        filmController = new FilmController(service);
    }

    /**
     * Проверяет, что контролер получает данные из сервиса и добавляет их в модель
     */
    @Test
    public void whenIndex() {
        List<Film> films = new ArrayList<>(Arrays.asList(
                new Film(1, "new film1", "new description1", new byte[]{1, 2}),
                new Film(1, "new film2", "new description2", new byte[]{2, 1})
        ));
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        when(service.findAll()).thenReturn(films);
        String page = filmController.index(session, model);
        verify(model).addAttribute("films", films);
        assertThat(page).isEqualTo("/index");
    }

    /**
     * проверяет что контроллер получает данные из сервиса и добавляет их в ResponseEntity
     */
    @Test
    public void whenDownloadPoster() {
        Optional<Film> film = Optional.of(new Film(1, "new film", "new description", new byte[]{1, 2}));
        when(service.findById(1)).thenReturn(film);
        ResponseEntity<Resource> responseEntity = filmController.downloadPoster(film.get().getId());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(new ByteArrayResource(film.get().getPoster()));
    }
}
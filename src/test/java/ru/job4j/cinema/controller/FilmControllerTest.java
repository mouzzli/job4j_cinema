package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilmControllerTest {
    private FilmService filmService;
    private FilmController filmController;

    @BeforeEach
    public void initFilmController() {
        filmService = Mockito.mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenGetFilmLibrary() {
        List<FilmDto> filmDtoList = List.of(new FilmDto("test", "testDescription", 2022, "horror", 12, 100, 1),
                new FilmDto("test2", "testDescription2", 2022, "comedy", 12, 100, 2));
        Model model = Mockito.mock(Model.class);
        Mockito.when(filmService.findAll()).thenReturn(filmDtoList);
        String page = filmController.filmLibrary(model);
        Mockito.verify(model).addAttribute("films", filmDtoList);
        assertThat(page).isEqualTo("filmLibrary");
    }
}
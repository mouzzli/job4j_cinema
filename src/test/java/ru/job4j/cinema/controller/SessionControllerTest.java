package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SessionControllerTest {

    private FilmSessionService filmSessionService;
    private SessionController sessionController;

    @BeforeEach
    public void initSessionController() {
        filmSessionService = Mockito.mock(FilmSessionService.class);
        sessionController = new SessionController(filmSessionService);
    }

    @Test
    public void whenGetSessionSchedule() {
        List<FilmSessionDto> filmSessionDtoList = List.of(
                new FilmSessionDto(1,
                        new FilmDto("test1", "testDescription", 2022, "Comedy", 12, 100, 1),
                        new Hall(1, "testHall1", 10, 20, "testHallDescription"),
                        LocalDateTime.of(2022, 2, 10, 12, 50),
                        LocalDateTime.of(2022, 2, 10, 14, 30)),
                new FilmSessionDto(2,
                        new FilmDto("test2", "testDescription2", 2022, "horror", 16, 60, 2),
                        new Hall(2, "testHall2", 8, 10, "testHallDescription2"),
                        LocalDateTime.of(2022, 2, 10, 10, 20),
                        LocalDateTime.of(2022, 2, 10, 11, 20)));

        Model model = Mockito.mock(Model.class);
        Mockito.when(filmSessionService.findAll()).thenReturn(filmSessionDtoList);
        String page = sessionController.sessionSchedule(model);
        Mockito.verify(model).addAttribute("filmSessions", filmSessionDtoList);
        assertThat(page).isEqualTo("schedule");
    }
}
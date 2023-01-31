package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.List;

public interface FilmSessionService {
    List<FilmSessionDto> findAll();

    FilmSessionDto findById(int id);
}

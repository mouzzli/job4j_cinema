package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {

    Optional<Film> save(Film film);

    boolean update(Film film);

    boolean deleteById(int id);

    List<Film> findAll();

    Optional<Film> findById(int id);
}

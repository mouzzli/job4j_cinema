package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    List<Film> findAll();

    Film save(Film film);

    boolean deleteById(int id);

    Optional<Film> findById(int id);
}

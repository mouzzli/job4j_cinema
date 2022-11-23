package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.JDBCFilmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JDBCFilmService implements FilmService {
    private final JDBCFilmRepository repository;

    public JDBCFilmService(JDBCFilmRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Film> save(Film film) {
        return repository.save(film);
    }

    @Override
    public boolean update(Film film) {
        return repository.update(film);
    }

    @Override
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    @Override
    public List<Film> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Film> findById(int id) {
        return repository.findById(id);
    }
}

package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

public interface FilmSessionRepository {

    List<Session> findAll();

    Session save(Session session);

    Optional<Session> findById(int id);

    boolean deleteById(int id);
}

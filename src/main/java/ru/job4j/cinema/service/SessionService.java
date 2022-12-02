package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {

    Optional<Session> save(Session session);

    List<Session> findAll();

    boolean deleteById(int id);
}

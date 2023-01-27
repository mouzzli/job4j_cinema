package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Optional;

public interface HallRepository {

    Optional<Hall> findById(int id);

    Hall save(Hall hall);

    List<Hall> findAll();

    boolean deleteById(int id);
}

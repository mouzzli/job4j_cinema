package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    List<Ticket> findAll();

    boolean deleteById(int id);

    Optional<Ticket> findById(int id);
}

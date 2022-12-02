package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JDBCSessionService implements SessionService {
    private final SessionRepository sessionRepository;

    public JDBCSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<Session> save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return sessionRepository.deleteById(id);
    }
}

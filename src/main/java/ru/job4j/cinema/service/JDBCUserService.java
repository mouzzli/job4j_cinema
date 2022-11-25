package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@Service
public class JDBCUserService implements UserService {
    private final UserRepository repository;

    public JDBCUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return repository.findUserByEmailAndPassword(email, password);
    }
}

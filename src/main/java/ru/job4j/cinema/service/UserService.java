package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

public interface UserService {

    User save(User user);

    User findByEmailAndPassword(String email, String password);
}

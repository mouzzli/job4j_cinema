package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        var userOptional =  userRepository.save(user);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }
        return userOptional.get();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        var optionalUser =  userRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с такой почтой или паролем не найден");
        }
        return optionalUser.get();
    }
}

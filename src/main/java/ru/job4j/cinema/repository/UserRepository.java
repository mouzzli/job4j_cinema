package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserRepository {

    /**
     * Сохраняет пользователя в БД
     *
     * @param user - модель данных пользователя
     * @return экземпляр сохраненного пользователя, если добавление прошло успешно,
     * в противном случае пустой Optional
     */
    Optional<User> save(User user);

    /**
     * Возвращает пользователя с данными параметрами
     * @param email - email пользователя
     * @param password - пароль пользователя
     * @return Возвращает пользователя с данными параметрами, в противном случае пустой Optional
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);

}

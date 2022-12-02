package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * Хранилище пользователей
 *
 * @see ru.job4j.cinema.model.User
 */
public interface UserRepository {

    /**
     * Выполняет сохранение пользователя. При успешном сохранении возвращает Optional
     * с объектом пользователя, у которого проинициализировано поле id. Иначе возвращает Optional.empty()
     * Сохранение может не произойти если, у сохраняемого пользователя почта или пароль совпадает с
     * почтой или паролем другого пользователя
     *
     * @param user - модель данных пользователя
     * @return Optional.of(User) при успешном сохранении, иначе Optional.empty()
     */
    Optional<User> save(User user);

    /**
     * Выполняет поиск пользователя по почте и паролю. При успешном поиске возвращает Optional
     * с объектом пользователя. Иначе возвращает Optional.empty()
     * Поиск может закончиться неудачно если почта и пароль пользователя не содержится в данном репозитории
     *
     * @param email    - email пользователя
     * @param password - пароль пользователя
     * @return Optional.of(User) если пользователь найден, иначе Optional.empty()
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);
}

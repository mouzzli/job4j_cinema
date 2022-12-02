package ru.job4j.cinema.repository;


import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище фильмов
 *
 * @see ru.job4j.cinema.model.Film
 */
public interface FilmRepository {
    /**
     * Возвращает список фильмов находящихся в данном репозитории
     *
     * @return List<Film>
     * </Film>
     */
    List<Film> findAll();

    /**
     * Выполняет поиск фильма с заданным id находящимся в данном репозитории.
     * При успешном поиске возвращает Optional с объектом пользователя.
     * Иначе возвращает Optional.empty()
     * Поиск может закончиться неудачно если фильм с заданным id не содержится в данном репозитории
     *
     * @param id - id фильма
     * @return Optional.of(Film) если фильм найден, иначе Optional.empty()
     */
    Optional<Film> findById(int id);

    /**
     * Выполняет сохранение фильма. При успешном сохранении возвращает Optional
     * с объектом фильма, у которого проинициализировано поле id. Иначе возвращает Optional.empty()
     * Сохранение может не произойти если, у сохраняемого фильма id совпадает с id другого фильма
     *
     * @param film - модель фильма
     * @return Optional.of(Film) при успешном сохранении, иначе Optional.empty()
     */
    Optional<Film> save(Film film);

    /**
     * Обновляет фильм. При успешном обновлении возвращает true, иначе вернёт false
     * Обновление может не произойти если фильм с заданным id не содержится в данном репозитории
     * @param film - модель фильма
     * @return true если обновление успешно, иначе false
     */
    boolean update(Film film);

    /**
     * Удаляет фильм с заданными id. При успешном удалении вернет true, иначе false
     * Удаление может не произойти если фильм с заданным id отсутствует в заданном репозитории
     *
     * @param id - id фильма
     * @return true при удачном удалении, иначе false
     */
    boolean deleteById(int id);
}

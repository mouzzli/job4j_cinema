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
     * Возвращает список всех фильмов
     *
     * @return список всех фильмов
     */
    List<Film> findAll();

    /**
     * Возвращает фильм с заданным id
     *
     * @param id фильма
     * @return фильм с заданным id если он не Null,
     * в противном случае пустой Optional
     */
    Optional<Film> findById(int id);

    /**
     * Сохраняет фильм в БД
     *
     * @param film
     * @return экземпляр сохраненного фильма, если добавление прошло успешно,
     * в противном случае пустой Optional
     */
    Optional<Film> save(Film film);

    /**
     * Обновляет фильм в БД
     *
     * @param film
     * @return true если обновление успешно,
     * в противном случае false
     */
    boolean update(Film film);

    /**
     * Удаляет фильм по заданному id
     *
     * @param id
     * @return true если удаление успешно,
     * в противном случае false
     */
    boolean deleteById(int id);
}

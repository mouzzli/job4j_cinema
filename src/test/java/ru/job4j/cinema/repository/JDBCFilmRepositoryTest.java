package ru.job4j.cinema.repository;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JDBCFilmRepositoryTest {
    private static final Logger LOG = Logger.getLogger(JDBCFilmRepositoryTest.class);
    private final BasicDataSource pool = new Main().loadPool();
    private final JDBCFilmRepository repository = new JDBCFilmRepository(pool);

    /**
     * Очищает таблицу после каждого теста
     */
    @AfterEach
    public void clearTable() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE from films")
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Сохраняет фильм в БД, затем получает его обратно
     */
    @Test
    public void whenSaveFilmThenGetTheSameFromDatabase() {
        Film film = new Film(1, "new Film", "new Description", new byte[]{1, 2});
        repository.save(film);
        Optional<Film> filmInDB = repository.findById(film.getId());
        assertThat(filmInDB.get()).isEqualTo(film);
    }

    /**
     * Удаляет фильм из БД, после получает Optional.isEmpty()
     */
    @Test
    public void whenDeleteFilmThenGetEmptyOptionalFromDatabase() {
        Film film = new Film(1, "new Film", "new Description", new byte[]{2, 1});
        repository.save(film);
        assertThat(repository.deleteById(film.getId())).isTrue();
        Optional<Film> filmInDB = repository.findById(film.getId());
        assertThat(filmInDB.isEmpty()).isTrue();
    }

    /**
     * Добавляет в БД список фильмов, затем получает их обратно
     */
    @Test
    public void whenSaveSomeFilmsThenGetFromDatabase() {
        Film film1 = new Film(1, "new Film1", "new Description1", new byte[]{1, 2});
        Film film2 = new Film(1, "new Film2", "new Description2", new byte[]{2, 1});
        Film film3 = new Film(1, "new Film3", "new Description3", new byte[]{3, 1});
        List<Film> filmList = new ArrayList<>(Arrays.asList(film1, film2, film3));
        repository.save(film1);
        repository.save(film2);
        repository.save(film3);
        List<Film> filmsInDB = repository.findAll();
        assertThat(filmsInDB).isEqualTo(filmList);
    }

    /**
     * Обновляет фильм в БД, затем получает обновленную версию
     */
    @Test
    public void whenUpdateThenGetFromDB() {
        Film film = new Film(1, "new Film1", "new Description1", new byte[]{1, 2});
        repository.save(film);
        film.setName("updated name");
        assertThat(repository.update(film)).isTrue();
        Optional<Film> filmInDB = repository.findById(film.getId());
        assertThat(filmInDB.get()).isEqualTo(film);
    }
}
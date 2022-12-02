package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JDBCSessionRepositoryTest {
    private static final Logger LOG = Logger.getLogger(JDBCFilmRepositoryTest.class);
    private final BasicDataSource pool = new Main().loadPool();
    private final JDBCSessionRepository sessionRepository = new JDBCSessionRepository(pool);
    private final JDBCFilmRepository filmRepository = new JDBCFilmRepository(pool);

    /**
     * Очищает таблицу после каждого теста
     */
    @AfterEach
    public void clearTable() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE from sessions")
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE from films")
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Сохраняет сеанс в БД, затем получает его обратно
     */
    @Test
    public void whenSaveSessionThenGetTheSameFromDatabase() {
        Film film = new Film(0, "newFilm", "newDescription", new byte[]{1, 2});
        filmRepository.save(film);
        Session session = new Session(0, film.getId(), LocalDateTime.of(2022, 12, 1, 12, 1));
        sessionRepository.save(session);
        Optional<Session> sessionInDb = sessionRepository.finById(session.getId());
        assertThat(sessionInDb.get()).isEqualTo(session);
    }

    /**
     * Добавляет в БД список сеансов, затем получает их обратно
     */
    @Test
    public void whenSaveSomeSessionsThenGetFromDatabase() {
        Film film = new Film(0, "newFilm", "newDescription", new byte[]{1, 2});
        filmRepository.save(film);
        Session session1 = new Session(0, film.getId(), LocalDateTime.of(2022, 12, 1, 12, 1));
        Session session2 = new Session(0, film.getId(), LocalDateTime.of(2022, 12, 2, 12, 1));
        Session session3 = new Session(0, film.getId(), LocalDateTime.of(2022, 12, 3, 12, 1));
        List<Session> sessions = new ArrayList<>(Arrays.asList(session1, session2, session3));
        sessionRepository.save(session1);
        sessionRepository.save(session2);
        sessionRepository.save(session3);
        List<Session> sessionsInDb = sessionRepository.findAll();
        assertThat(sessionsInDb).isEqualTo(sessions);
    }

    /**
     * Добавляет сеанс, удаляет сеанс из БД, после получает Optional.isEmpty()
     */
    @Test
    public void whenDeleteSessionThenGetEmptyOptionalFromDatabase() {
        Film film = new Film(0, "new Film", "new Description", new byte[]{2, 1});
        filmRepository.save(film);
        Session session = new Session(0, film.getId(), LocalDateTime.of(2022, 12, 1, 12, 1));
        sessionRepository.save(session);
        assertThat(sessionRepository.deleteById(session.getId())).isTrue();
        Optional<Session> sessionInDb = sessionRepository.finById(session.getId());
        assertThat(sessionInDb.isEmpty()).isTrue();
    }
}
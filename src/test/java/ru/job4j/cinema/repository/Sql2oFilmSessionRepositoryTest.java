package ru.job4j.cinema.repository;

import org.junit.jupiter.api.*;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmSessionRepositoryTest {

    static Sql2oFilmRepository sql2oFilmRepository;
    static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    static Sql2oHallRepository sql2oHallRepository;
    static Sql2oGenreRepository sql2oGenreRepository;
    static Sql2oFileRepository sql2oFileRepository;

    private static Film film;
    private static Hall hall;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }

        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        var genre = sql2oGenreRepository.save(new Genre("comedy"));
        var file = sql2oFileRepository.save(new File("test", "test"));

        film = sql2oFilmRepository.save(new Film("test", "testDescription", 2022, genre.getId(), 12, 90, file.getId()));
        hall = sql2oHallRepository.save(new Hall("testHallName", 10, 20, "testHallDescription"));
    }

    @AfterAll
    public static void clearRepositories() {
        var filmSessions = sql2oFilmRepository.findAll();
        for (Film filmSession : filmSessions) {
            sql2oFilmRepository.deleteById(filmSession.getId());
        }

        var genres = sql2oGenreRepository.findAll();
        for (Genre genre : genres) {
            sql2oGenreRepository.deleteById(genre.getId());
        }

        var files = sql2oFileRepository.findAll();
        for (File file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }

        var halls = sql2oHallRepository.findAll();
        for (Hall hall : halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }
    }

    @AfterEach
    public void clearFilmSessions() {
        var filmSessions = sql2oFilmSessionRepository.findAll();
        for (Session filmSession : filmSessions) {
            sql2oFilmSessionRepository.deleteById(filmSession.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var filmSession = sql2oFilmSessionRepository.save(new Session(
                film.getId(),
                hall.getId(),
                LocalDateTime.of(2022, 2, 1, 14, 0),
                LocalDateTime.of(2022, 2, 1, 15, 30)));
        var savedFilmSession = sql2oFilmSessionRepository.findById(filmSession.getId()).get();
        assertThat(savedFilmSession).isEqualTo(filmSession);
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oFilmSessionRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteByIdThenGetTrue() {
        var filmSession = sql2oFilmSessionRepository.save(new Session(
                film.getId(),
                hall.getId(),
                LocalDateTime.of(2022, 2, 1, 14, 0),
                LocalDateTime.of(2022, 2, 1, 15, 30)));
        assertThat(sql2oFilmSessionRepository.deleteById(filmSession.getId())).isTrue();
    }
}
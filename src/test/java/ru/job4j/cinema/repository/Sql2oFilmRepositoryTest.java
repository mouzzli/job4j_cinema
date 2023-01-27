package ru.job4j.cinema.repository;

import org.junit.jupiter.api.*;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class Sql2oFilmRepositoryTest {
    static Sql2oFilmRepository sql2oFilmRepository;
    static Sql2oGenreRepository sql2oGenreRepository;
    static Sql2oFileRepository sql2oFileRepository;
    private static File file;
    private static Genre genre;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = sql2oFileRepository.save(new File("test", "test"));
        genre = sql2oGenreRepository.save(new Genre("comedy"));
    }

    @AfterAll
    public static void clearFiles() {
        var files = sql2oFileRepository.findAll();
        for (File file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }

    }

    @AfterAll
    public static void clearGenres() {
        var genres = sql2oGenreRepository.findAll();
        for (Genre genre : genres) {
            sql2oGenreRepository.deleteById(genre.getId());
        }
    }

    @AfterEach
    public void clearFilms() {
        var films = sql2oFilmRepository.findAll();
        for (Film film : films) {
            sql2oFilmRepository.deleteById(film.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var film = sql2oFilmRepository.save(new Film("film_1",
                "film1_description",
                2022,
                genre.getId(),
                12,
                120,
                file.getId()));
        var savedFilm = sql2oFilmRepository.findById(film.getId()).get();
        assertThat(savedFilm).isEqualTo(film);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var film1 = sql2oFilmRepository.save(new Film("film_1",
                "film1_description",
                2022,
                genre.getId(),
                12,
                120,
                file.getId()));
        var film2 = sql2oFilmRepository.save(new Film("film_2",
                "film2_description",
                2021,
                genre.getId(),
                12,
                120,
                file.getId()));
        var film3 = sql2oFilmRepository.save(new Film("film_3",
                "film3_description",
                2020,
                genre.getId(),
                12,
                120,
                file.getId()));
        var result = sql2oFilmRepository.findAll();
        assertThat(result).isEqualTo(List.of(film1, film2, film3));
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oFilmRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteByIdThenGetTrue() {
        var film = sql2oFilmRepository.save(new Film("film_1",
                "film1_description",
                2022,
                genre.getId(),
                12,
                120,
                file.getId()));
        assertThat(sql2oFilmRepository.deleteById(film.getId())).isTrue();
    }
}

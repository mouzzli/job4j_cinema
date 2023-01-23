package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.Genre;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oGenreRepositoryTest {
    static Sql2oGenreRepository sql2oGenreRepository;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }

    @AfterEach
    public void clearGenres() {
        var genres = sql2oGenreRepository.findAll();
        for (Genre genre : genres) {
            sql2oGenreRepository.deleteById(genre.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var genre = sql2oGenreRepository.save(new Genre("test"));
        var savedGenre = sql2oGenreRepository.findById(genre.getId());
        assertThat(savedGenre.get()).isEqualTo(genre);
    }

    @Test
    public void whenDeleteInvalidThenGetFalse() {
        assertThat(sql2oGenreRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteAndGetTrue() {
        var genre = sql2oGenreRepository.save(new Genre("test"));
        assertThat(sql2oGenreRepository.deleteById(genre.getId())).isTrue();
    }
}
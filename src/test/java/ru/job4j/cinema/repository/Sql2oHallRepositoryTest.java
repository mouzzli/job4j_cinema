package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oHallRepositoryTest {

    static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @AfterEach
    public void clearHalls() {
        var halls = sql2oHallRepository.findAll();
        for (Hall hall : halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var hall = sql2oHallRepository.save(new Hall("test12", 10, 20, "testDescription"));
        var savedHall = sql2oHallRepository.findById(hall.getId()).get();
        assertThat(savedHall).isEqualTo(hall);
    }

    @Test
    public void whenDeleteInvalidThenGetFalse() {
        assertThat(sql2oHallRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteAndGetTrue() {
        var hall = sql2oHallRepository.save(new Hall("test12", 10, 20, "testDescription"));
        assertThat(sql2oHallRepository.deleteById(hall.getId())).isTrue();
    }
}
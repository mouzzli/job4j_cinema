package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.File;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFileRepositoryTest {

    static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @AfterEach
    public void clearFiles() {
        var files = sql2oFileRepository.findAll();
        for (File file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var file = sql2oFileRepository.save(new File("test", "test"));
        var savedFile = sql2oFileRepository.findById(file.getId()).get();
        assertThat(savedFile).isEqualTo(file);
    }

    @Test
    public void whenDeleteInvalidThenGetFalse() {
        assertThat(sql2oFileRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteAndGetTrue() {
        var file = sql2oFileRepository.save(new File("test", "test"));
        assertThat(sql2oFileRepository.deleteById(file.getId())).isTrue();
    }
}
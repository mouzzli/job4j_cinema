package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.User;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oUserRepositoryTest {

    static Sql2oUserRepository sql2oUserRepository;

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

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (User user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var user = sql2oUserRepository.save(new User("TEST", "test@mail.ru", "pass"));
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword());
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void whenDeleteInvalidThenGetFalse() {
        assertThat(sql2oUserRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteAndGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User("TEST", "test@mail.ru", "pass"));
        assertThat(sql2oUserRepository.deleteById(user.get().getId())).isTrue();
        assertThat(sql2oUserRepository.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword()).isEmpty()).isTrue();
    }

    @Test
    public void whenSavedUserAlreadyExistThenGetEmptyOptional() {
        sql2oUserRepository.save(new User("TEST", "test@mail.ru", "pass"));
        var existingUser = sql2oUserRepository.save(new User("TEST", "test@mail.ru", "pass"));
        assertThat(existingUser).isEqualTo(Optional.empty());
    }
}
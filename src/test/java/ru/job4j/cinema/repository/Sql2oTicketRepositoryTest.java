package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.DatasourceConfiguration;
import ru.job4j.cinema.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oTicketRepositoryTest {

    static Sql2oTicketRepository sql2oTicketRepository;
    static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    static Sql2oUserRepository sql2oUserRepository;
    static Sql2oFilmRepository sql2oFilmRepository;
    static Sql2oHallRepository sql2oHallRepository;
    static Sql2oFileRepository sql2oFileRepository;
    static Sql2oGenreRepository sql2oGenreRepository;
    private static User user;
    private static Session session;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);

        var file = sql2oFileRepository.save(new File("test", "test"));
        var genre = sql2oGenreRepository.save(new Genre("comedy"));
        var film = sql2oFilmRepository.save(new Film("test", "testDescription", 2022, genre.getId(), 12, 90, file.getId()));
        var hall = sql2oHallRepository.save(new Hall("testHallName", 10, 20, "testHallDescription"));

        session = sql2oFilmSessionRepository.save(new Session(film.getId(),
                hall.getId(),
                LocalDateTime.of(2022, 2, 2, 14, 0),
                LocalDateTime.of(2022, 2, 2, 15, 30))
        );
        user = sql2oUserRepository.save(new User("testUser", "testEmail", "testPassword")).get();

    }

    @AfterAll
    public static void clearRepositories() {
        var users = sql2oUserRepository.findAll();
        for (User user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }

        var sessions = sql2oFilmSessionRepository.findAll();
        for (Session session : sessions) {
            sql2oFilmSessionRepository.deleteById(session.getId());
        }

        var halls = sql2oHallRepository.findAll();
        for (Hall hall : halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }

        var films = sql2oFilmRepository.findAll();
        for (Film film : films) {
            sql2oFilmRepository.deleteById(film.getId());
        }

        var files = sql2oFileRepository.findAll();
        for (File file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }

        var genres = sql2oGenreRepository.findAll();
        for (Genre genre : genres) {
            sql2oGenreRepository.deleteById(genre.getId());
        }
    }

    @AfterEach
    public void clearTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (Ticket ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @Test
    public void whenSaveThenGet() {
        var ticket = sql2oTicketRepository.save(new Ticket(session.getId(),
                1,
                2,
                user.getId())).get();
        var savedTicket = sql2oTicketRepository.findById(ticket.getId()).get();
        assertThat(savedTicket).isEqualTo(ticket);
    }

    @Test
    public void whenDeleteInvalidThenGetFalse() {
        assertThat(sql2oTicketRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenSaveThenDeleteAndGetEmptyOptional() {
        var ticket = sql2oTicketRepository.save(new Ticket(session.getId(),
                1,
                2,
                user.getId())).get();
        assertThat(sql2oTicketRepository.deleteById(ticket.getId())).isTrue();
        assertThat(sql2oTicketRepository.findById(ticket.getId())).isEqualTo(Optional.empty());
    }

    @Test
    public void whenSavedUserAlreadyExistThenGetEmptyOptional() {
        sql2oTicketRepository.save(new Ticket(session.getId(),
                1,
                2,
                user.getId()));

        var existingTicket = sql2oTicketRepository.save(new Ticket(session.getId(),
                1,
                2,
                user.getId()));
        assertThat(existingTicket).isEqualTo(Optional.empty());
    }

}
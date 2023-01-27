package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {
    private static final String FIND_ALL = "SELECT * FROM film_sessions ORDER BY start_time";
    private static final String SAVE = "INSERT INTO film_sessions (film_id, halls_id, start_time, end_time) "
            + "VALUES (:filmId, :hallsId, :startTime, :endTime)";
    private static final String FIND_BY_ID = "SELECT * FROM film_sessions WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM film_sessions WHERE id = :id";
    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Session> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetch(Session.class);
        }
    }

    @Override
    public Session save(Session session) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SAVE, true)
                    .addParameter("filmId", session.getFilmId())
                    .addParameter("hallsId", session.getHallId())
                    .addParameter("startTime", session.getStartTime())
                    .addParameter("endTime", session.getEndTime());
            int generatedKey = query.setColumnMappings(Session.COLUMN_MAPPING).executeUpdate().getKey(Integer.class);
            session.setId(generatedKey);
            return session;
        }
    }

    @Override
    public Optional<Session> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID)
                    .addParameter("id", id);
            var session = query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetchFirst(Session.class);
            return Optional.ofNullable(session);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID)
                    .addParameter("id", id);
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}

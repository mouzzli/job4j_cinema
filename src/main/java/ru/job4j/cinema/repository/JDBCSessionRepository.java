package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCSessionRepository implements SessionRepository {
    private static final Logger LOG = Logger.getLogger(JDBCSessionRepository.class);
    private static final String SAVE = "INSERT INTO sessions (film_id, date_time) VALUES (?, ?)";
    private static final String FIND_ALL = "SELECT * FROM sessions";
    private static final String DELETE_BY_ID = "DELETE FROM sessions WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM sessions WHERE id = ?";
    private final BasicDataSource pool;

    public JDBCSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<Session> save(Session session) {
        Optional<Session> sessionOptional = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, session.getFilmId());
            ps.setTimestamp(2, Timestamp.valueOf(session.getDateTime()));
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                while (it.next()) {
                    session.setId(it.getInt(1));
                }
                sessionOptional = Optional.of(session);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return sessionOptional;
    }

    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement(FIND_ALL);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    sessions.add(getSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return sessions;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE_BY_ID)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<Session> finById(int id) {
        Optional<Session> optionalSession = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    optionalSession = Optional.of(getSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return optionalSession;
    }

    private Session getSession(ResultSet it) throws SQLException {
        return new Session(
                it.getInt("id"),
                it.getInt("film_id"),
                it.getTimestamp("date_time").toLocalDateTime()
        );
    }
}

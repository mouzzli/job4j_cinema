package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JDBCUserRepository implements UserRepository {
    private static final Logger LOG = Logger.getLogger(JDBCUserRepository.class);
    private static final String SAVE = "INSERT INTO users (username, email, phone, password)  VALUES (?, ?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private final BasicDataSource pool;

    public JDBCUserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<User> save(User user) {
        Optional<User> userOptional = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    user.setId(it.getInt(1));
                }
                userOptional = Optional.of(user);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return userOptional;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    userOptional = Optional.of(getUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return userOptional;
    }

    private User getUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("username"),
                it.getString("email"),
                it.getString("phone"),
                it.getString("password"),
                it.getBoolean("isAdmin")
        );
    }
}

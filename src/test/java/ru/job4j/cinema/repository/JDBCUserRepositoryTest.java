package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.assertj.core.api.Assertions.assertThat;

class JDBCUserRepositoryTest {
    private static final Logger LOG = Logger.getLogger(JDBCFilmRepositoryTest.class);
    private final BasicDataSource pool = new Main().loadPool();
    private final UserRepository userRepository = new JDBCUserRepository(pool);

    /**
     * Очищает таблицу после каждого теста
     */
    @AfterEach
    public void clearTable() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE from users")
        ) {
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Сохраняет пользователя в БД, затем получает его обратно
     */
    @Test
    public void whenSaveUserThenGetTheSameFromDatabase() {
        User user = new User(1, "name", "email", "phone", "password");
        userRepository.save(user);
        assertThat(userRepository.findUserByEmailAndPassword("email", "password").get()).isEqualTo(user);
    }
}
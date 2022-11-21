package ru.job4j.cinema.repository;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Реализация хранилища фильмов использующая JDBC
 */
@Repository
public class JDBCFilmRepository implements FilmRepository {
    private static final Logger LOG = Logger.getLogger(JDBCFilmRepository.class);
    private static final String FIND_ALL = "SELECT * FROM films";
    private static final String FIND_BY_ID = "SELECT * FROM films WHERE id = ?";
    private static final String SAVE = "INSERT INTO films(name, description, poster) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE films SET name = ?, description = ?, poster = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM films WHERE id = ?";
    private final BasicDataSource pool;

    public JDBCFilmRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<Film> save(Film film) {
        Optional<Film> filmOptional = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setBytes(3, film.getPoster());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                while (it.next()) {
                    film.setId(it.getInt(1));
                }
                filmOptional = Optional.of(film);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return filmOptional;
    }

    @Override
    public boolean update(Film film) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)
        ) {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setBytes(3, film.getPoster());
            ps.setInt(4, film.getId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Film> findAll() {
        List<Film> filmList = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    filmList.add(getFilm(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return filmList;
    }

    @Override
    public Optional<Film> findById(int id) {
        Optional<Film> film = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    film = Optional.of(getFilm(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return film;
    }

    private Film getFilm(ResultSet it) throws Exception {
        return new Film(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getBytes("poster")
        );
    }
}

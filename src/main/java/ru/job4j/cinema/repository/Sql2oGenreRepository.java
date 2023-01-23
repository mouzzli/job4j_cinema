package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class Sql2oGenreRepository implements GenreRepository {
    private static final String FIND_BY_ID = "SELECT * FROM genres WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM genres WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM genres";
    private static final String SAVE = "INSERT INTO genres (name) VALUES (:name)";
    private final Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            query.addParameter("id", id);
            var genre = query.executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID)
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public List<Genre> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.executeAndFetch(Genre.class);
        }
    }

    @Override
    public Genre save(Genre genre) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SAVE, true)
                    .addParameter("name", genre.getName());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            genre.setId(generatedId);
            return genre;
        }
    }
}

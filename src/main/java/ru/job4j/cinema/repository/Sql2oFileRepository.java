package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.List;
import java.util.Optional;

@Repository
public class Sql2oFileRepository implements FileRepository {
    private static final String FIND_BY_ID = "SELECT * FROM files WHERE id = :id";
    private static final String SAVE = "INSERT INTO files(name, path) VALUES (:name, :path)";
    private static final String FIND_ALL = "SELECT * FROM files";
    private static final String DELETE_BY_ID = "DELETE FROM files WHERE id = :id";

    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }

    @Override
    public File save(File file) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SAVE, true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            file.setId(generatedId);
            return file;
        }
    }

    @Override
    public List<File> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.executeAndFetch(File.class);
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
}

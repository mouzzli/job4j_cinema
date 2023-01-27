package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Optional;

@Repository
public class Sql2oHallRepository implements HallRepository {
    private static final String FIND_BY_ID = "SELECT * FROM halls WHERE id = :id";
    private static final String SAVE = "INSERT INTO halls(name, row_count, place_count, description) "
            + "VALUES (:name, :rowCount, :placeCount, :description)";
    private static final String FIND_ALL = "SELECT * FROM halls";
    private static final String DELETE_BY_ID = "DELETE FROM halls WHERE id = :id";
    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID)
                    .addParameter("id", id);
            var hall = query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }

    @Override
    public Hall save(Hall hall) {
        try (var connection = sql2o.open()) {
            var query  = connection.createQuery(SAVE, true)
                    .addParameter("name", hall.getName())
                    .addParameter("rowCount", hall.getRowCount())
                    .addParameter("placeCount", hall.getPlaceCount())
                    .addParameter("description", hall.getDescription());
            int generatedKey = query.executeUpdate().getKey(Integer.class);
            hall.setId(generatedKey);
            return hall;
        }
    }

    @Override
    public List<Hall> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetch(Hall.class);
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

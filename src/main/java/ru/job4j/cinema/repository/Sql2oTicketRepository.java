package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {
    private static final String SAVE = "INSERT INTO tickets (session_id, row_number, place_number, user_id)"
            + "VALUES (:sessionId, :rowNumber, :placeNumber, :userId)";
    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SAVE, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedKey = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeUpdate().getKey(Integer.class);
            ticket.setId(generatedKey);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(ticket);
    }
}

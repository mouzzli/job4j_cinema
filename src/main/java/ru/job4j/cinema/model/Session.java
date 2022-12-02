package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {
    private int id;
    private int filmId;
    private LocalDateTime dateTime;

    public Session(int id, int filmId, LocalDateTime dateTime) {
        this.id = id;
        this.filmId = filmId;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session session)) {
            return false;
        }
        return getId() == session.getId() && getFilmId() == session.getFilmId() && Objects.equals(getDateTime(), session.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFilmId(), getDateTime());
    }

    @Override
    public String toString() {
        return "Session{"
                + "id=" + id
                + ", filmId=" + filmId
                + ", dateTime=" + dateTime
                + '}';
    }
}

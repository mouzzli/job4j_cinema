package ru.job4j.cinema.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Модель данных фильма
 */
public class Film {
    /**
     * Идентификатор задачи
     */
    private int id;
    /**
     * Название фильма
     */
    private String name;
    /**
     * Описание фильма
     */
    private String description;
    /**
     * постер фильма
     */
    private byte[] poster;

    public Film(int id, String name, String description, byte[] poster) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Film film)) {
            return false;
        }
        return id == film.id && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Arrays.equals(poster, film.poster);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description);
        result = 31 * result + Arrays.hashCode(poster);
        return result;
    }
}

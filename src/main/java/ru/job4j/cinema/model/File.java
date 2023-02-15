package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных файла
 */
public class File {
    /**
     * Идентификатор файла
     */
    private int id;

    /**
     * Имя файла
     */
    private String name;
    /**
     * Путь файла
     */
    private String path;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public File(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return id == file.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

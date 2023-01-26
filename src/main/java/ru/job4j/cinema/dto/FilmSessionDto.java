package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Hall;

import java.time.LocalDateTime;
import java.util.Objects;

public class FilmSessionDto {
    private int id;
    private FilmDto filmDto;
    private Hall hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public FilmSessionDto(int id, ru.job4j.cinema.dto.FilmDto filmDto, Hall hall, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.filmDto = filmDto;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ru.job4j.cinema.dto.FilmDto getFilmDto() {
        return filmDto;
    }

    public void setFilmDto(ru.job4j.cinema.dto.FilmDto filmDto) {
        this.filmDto = filmDto;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSessionDto that = (FilmSessionDto) o;
        return id == that.id && Objects.equals(filmDto, that.filmDto) && Objects.equals(hall, that.hall) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmDto, hall, startTime, endTime);
    }
}

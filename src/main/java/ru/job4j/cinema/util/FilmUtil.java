package ru.job4j.cinema.util;

import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

public class FilmUtil {

    public static FilmDto createFilmDto(Film film, Genre genre) {
        return new FilmDto(film.getName(),
                film.getDescription(),
                film.getYear(),
                genre.getName(),
                film.getMinimalAge(),
                film.getDurationInMinutes(),
                film.getFileId());
    }
}

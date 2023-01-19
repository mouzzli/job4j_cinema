package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public FilmServiceImpl(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<FilmDto> findAll() {
        var films = filmRepository.findAll();
        return films.stream().map(this::convertToFilmDto).collect(Collectors.toList());
    }

    private FilmDto convertToFilmDto(Film film) {
        return new FilmDto(
                film.getName(),
                film.getDescription(),
                film.getYear(),
                genreRepository.findById(film.getGenreId()).get().getName(),
                film.getMinimalAge(),
                film.getDurationInMinutes(),
                film.getFileId());
    }
}

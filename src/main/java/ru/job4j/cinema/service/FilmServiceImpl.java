package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.job4j.cinema.util.FilmUtil.createFilmDto;

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
        List<FilmDto> filmDtoList = new ArrayList<>();
        var films = filmRepository.findAll();
        for (Film film : films) {
            var genre = genreRepository.findById(film.getGenreId()).get();
            filmDtoList.add(createFilmDto(film, genre));
        }
        return filmDtoList;
    }
}

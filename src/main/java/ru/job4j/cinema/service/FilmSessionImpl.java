package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.GenreRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.job4j.cinema.util.FilmUtil.createFilmDto;

@Service
public class FilmSessionImpl implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final GenreRepository genreRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public FilmSessionImpl(FilmSessionRepository filmSessionRepository, FilmRepository filmRepository, HallRepository hallRepository, GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
        this.hallRepository = hallRepository;
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public List<FilmSessionDto> findAll() {
        List<FilmSessionDto> filmSessionDtoList = new ArrayList<>();
        var filmSession = filmSessionRepository.findAll();
        for (Session session : filmSession) {
            var film = filmRepository.findById(session.getFilmId()).get();
            var genre = genreRepository.findById(film.getGenreId()).get();
            var hall = hallRepository.findById(session.getHallId()).get();
            filmSessionDtoList.add(new FilmSessionDto(session.getId(),
                    createFilmDto(film, genre),
                    hall,
                    session.getStartTime(),
                    session.getEndTime()));
        }
        return filmSessionDtoList;
    }
}

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
public class FilmSessionServiceImpl implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final GenreRepository genreRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public FilmSessionServiceImpl(FilmSessionRepository filmSessionRepository, FilmRepository filmRepository, HallRepository hallRepository, GenreRepository genreRepository) {
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
            filmSessionDtoList.add(createFilmSessionDto(session));
        }
        return filmSessionDtoList;
    }

    private FilmSessionDto createFilmSessionDto(Session session) {
        var film = filmRepository.findById(session.getFilmId()).get();
        var genre = genreRepository.findById(film.getGenreId()).get();
        var hall = hallRepository.findById(session.getHallId()).get();
        return new FilmSessionDto(session.getId(),
                createFilmDto(film, genre),
                hall,
                session.getStartTime(),
                session.getEndTime());
    }

    @Override
    public FilmSessionDto findById(int id) {
        return createFilmSessionDto(filmSessionRepository.findById(id).get());
    }
}

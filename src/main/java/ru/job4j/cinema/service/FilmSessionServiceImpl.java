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
import java.util.NoSuchElementException;

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
        var film = filmRepository.findById(session.getFilmId());
        if (film.isEmpty()) {
            throw new NoSuchElementException("Film can't be empty");
        }
        var genre = genreRepository.findById(film.get().getGenreId());
        if (genre.isEmpty()) {
            throw new NoSuchElementException("Genre can't be empty");
        }
        var hall = hallRepository.findById(session.getHallId());
        if (hall.isEmpty()) {
            throw new NoSuchElementException("Hall can't be empty");
        }
        return new FilmSessionDto(session.getId(),
                createFilmDto(film.get(), genre.get()),
                hall.get(),
                session.getStartTime(),
                session.getEndTime());
    }

    @Override
    public FilmSessionDto findById(int id) {
        var optionalFilmSession = filmSessionRepository.findById(id);
        if (optionalFilmSession.isEmpty()) {
            throw new NoSuchElementException("Session can't be empty");
        }
        return createFilmSessionDto(optionalFilmSession.get());
    }
}

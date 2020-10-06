package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.dao.MovieDao;
import com.hibernate.cinema.dao.MovieSessionDao;
import com.hibernate.cinema.lib.Inject;
import com.hibernate.cinema.lib.Service;
import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
    @Inject
    private MovieSessionDao movieSessionDao;

    @Inject
    private MovieDao movieDao;

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        Movie movie = movieDao.get(movieId);
        return movieSessionDao.getAvailable(movie, date);
    }

    @Override
    public MovieSession add(MovieSession session) {
        return movieSessionDao.add(session);
    }
}

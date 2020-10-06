package com.hibernate.cinema.dao;

import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.MovieSession;
import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    List<MovieSession> getAvailable(Movie movie, LocalDate date);
}

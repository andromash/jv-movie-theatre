package com.hibernate.cinema.dao;

import com.hibernate.cinema.model.MovieSession;
import java.util.List;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession);

    List<MovieSession> getAll();
}

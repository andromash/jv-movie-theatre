package com.hibernate.cinema.dao;

import com.hibernate.cinema.model.Movie;
import java.util.List;

public interface MovieDao {
    Movie add(Movie movie);

    List<Movie> getAll();
}

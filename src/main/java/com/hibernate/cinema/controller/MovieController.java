package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.dto.MovieRequestDto;
import com.hibernate.cinema.model.dto.MovieResponseDto;
import com.hibernate.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;

import com.hibernate.cinema.service.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping
    public void addMovie(@RequestBody MovieRequestDto movieRequestDto) {
        movieService.add(movieMapper.castDtoToMovie(movieRequestDto));
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll().stream()
                .map(movieMapper::castMovieToDto)
                .collect(Collectors.toList());
    }
}

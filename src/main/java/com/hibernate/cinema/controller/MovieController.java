package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.dto.MovieRequestDto;
import com.hibernate.cinema.model.dto.MovieResponseDto;
import com.hibernate.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
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

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public void addMovie(@RequestBody MovieRequestDto movieRequestDto) {
        movieService.add(castDtoToMovie(movieRequestDto));
    }

    @GetMapping("/all")
    public List<MovieResponseDto> getAll() {
        return movieService.getAll().stream()
                .map(this::castMovieToDto)
                .collect(Collectors.toList());
    }

    private Movie castDtoToMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setDescription(movieRequestDto.getDescription());
        movie.setTitle(movieRequestDto.getTitle());
        return movie;
    }

    private MovieResponseDto castMovieToDto(Movie movie) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setDescription(movie.getDescription());
        movieResponseDto.setMovieId(movie.getId());
        movieResponseDto.setTitle(movie.getTitle());
        return movieResponseDto;
    }
}

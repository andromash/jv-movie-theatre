package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.model.dto.MovieSessionRequestDto;
import com.hibernate.cinema.model.dto.MovieSessionResponseDto;
import com.hibernate.cinema.service.CinemaHallService;
import com.hibernate.cinema.service.MovieService;
import com.hibernate.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieService movieService,
                                  CinemaHallService cinemaHallService) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    @PostMapping
    public void addMovieSession(@RequestBody MovieSessionRequestDto movieSessionDto) {
        movieSessionService.add(castDtoToMovieSession(movieSessionDto));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getAvailable(@RequestParam Long movieId,
                                                      @RequestParam LocalDate date) {
        return movieSessionService.findAvailableSessions(movieId, date).stream()
                .map(this::castMovieSessionToDto)
                .collect(Collectors.toList());
    }

    private MovieSession castDtoToMovieSession(MovieSessionRequestDto movieSessionDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.parse(movieSessionDto.getShowTime(),
                DateTimeFormatter.ofPattern("d-MM-yyyy hh:mm:ss a")));
        movieSession.setMovie(movieService.getById(movieSessionDto.getMovieId()));
        movieSession.setCinemaHall(cinemaHallService.getById(movieSessionDto.getCinemaHallId()));
        return movieSession;
    }

    private MovieSessionResponseDto castMovieSessionToDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setCinemaHall(movieSession.getCinemaHall().getId());
        movieSessionResponseDto.setMovie(movieSession.getMovie().getId());
        movieSessionResponseDto.setShowTime(movieSession.getShowTime().toString());
        return movieSessionResponseDto;
    }
}

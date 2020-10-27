package com.hibernate.cinema.service.mapper;

import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.model.dto.MovieSessionRequestDto;
import com.hibernate.cinema.model.dto.MovieSessionResponseDto;
import com.hibernate.cinema.service.CinemaHallService;
import com.hibernate.cinema.service.MovieService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper {
    private static final String DATETIME_PATTERN = "d-MM-yyyy hh:mm:ss a";
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    @Autowired
    public MovieSessionMapper(MovieService movieService, CinemaHallService cinemaHallService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    public MovieSession mapDtoToMovieSession(MovieSessionRequestDto movieSessionDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.parse(movieSessionDto.getShowTime(),
                DateTimeFormatter.ofPattern(DATETIME_PATTERN)));
        movieSession.setMovie(movieService.getById(movieSessionDto.getMovieId()));
        movieSession.setCinemaHall(cinemaHallService.getById(movieSessionDto.getCinemaHallId()));
        movieSession.setId(movieSessionDto.getId());
        return movieSession;
    }

    public MovieSessionResponseDto mapMovieSessionToDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setCinemaHall(movieSession.getCinemaHall().getId());
        movieSessionResponseDto.setMovie(movieSession.getMovie().getId());
        movieSessionResponseDto.setShowTime(movieSession.getShowTime().toString());
        return movieSessionResponseDto;
    }
}

package com.hibernate.cinema;

import com.hibernate.cinema.lib.Injector;
import com.hibernate.cinema.model.CinemaHall;
import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.service.CinemaHallService;
import com.hibernate.cinema.service.MovieService;
import com.hibernate.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.hibernate.cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movie = movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(280);
        imax.setDescription("IMAX");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        imax = cinemaHallService.add(imax);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(imax);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.now()).forEach(System.out::println);
    }
}

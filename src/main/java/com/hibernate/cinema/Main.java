package com.hibernate.cinema;

import com.hibernate.cinema.lib.Injector;
import com.hibernate.cinema.model.CinemaHall;
import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.AuthenticationService;
import com.hibernate.cinema.service.CinemaHallService;
import com.hibernate.cinema.service.MovieService;
import com.hibernate.cinema.service.MovieSessionService;
import com.hibernate.cinema.service.OrderService;
import com.hibernate.cinema.service.ShoppingCartService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;

public class Main {
    private static Injector injector = Injector.getInstance("com.hibernate.cinema");
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movie = movieService.add(movie);
        movieService.getAll().forEach(logger::info);

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(280);
        imax.setDescription("IMAX");
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        imax = cinemaHallService.add(imax);
        cinemaHallService.getAll().forEach(logger::info);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(imax);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.now()).forEach(logger::info);

        AuthenticationService authenticationService
                = (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User andrii = authenticationService
                .register("andriiromash@gmail.com", "abrakadabra");

        ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, andrii);
        shoppingCartService.addSession(movieSession, andrii);

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(andrii);
        orderService.completeOrder(shoppingCart.getTickets(), andrii);
        orderService.getOrderHistory(andrii).forEach(logger::info);
    }
}

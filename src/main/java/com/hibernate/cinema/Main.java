package com.hibernate.cinema;

import com.hibernate.cinema.config.AppConfig;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = context.getBean(MovieService.class);
        movie = movieService.add(movie);
        movieService.getAll().forEach(logger::info);

        CinemaHall imax = new CinemaHall();
        imax.setCapacity(280);
        imax.setDescription("IMAX");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        imax = cinemaHallService.add(imax);
        cinemaHallService.getAll().forEach(logger::info);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(imax);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.now()).forEach(logger::info);

        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
        User andrii = authenticationService
                .register("andriiromash@gmail.com", "abrakadabra");

        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(movieSession, andrii);
        shoppingCartService.addSession(movieSession, andrii);

        OrderService orderService = context.getBean(OrderService.class);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(andrii);
        orderService.completeOrder(shoppingCart.getTickets(), andrii);
        orderService.getOrderHistory(andrii).forEach(logger::info);
    }
}

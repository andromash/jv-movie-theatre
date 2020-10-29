package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.service.MovieSessionService;
import com.hibernate.cinema.service.ShoppingCartService;
import com.hibernate.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final MovieSessionService movieSessionService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  MovieSessionService movieSessionService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.movieSessionService = movieSessionService;
    }

    @GetMapping("/by-user")
    public ShoppingCart getByUser(Authentication user) {
        return shoppingCartService.getByUser(userService.findByEmail(user.getName()));
    }

    @PostMapping("/movie-sessions")
    public void addMovieSession(Authentication user,
                                @RequestParam Long movieSessionId) {
        shoppingCartService.addSession(movieSessionService.getById(movieSessionId),
                userService.findByEmail(user.getName()));
    }
}

package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.dto.MovieSessionRequestDto;
import com.hibernate.cinema.service.ShoppingCartService;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.service.mapper.MovieSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final MovieSessionMapper movieSessionMapper;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService, MovieSessionMapper movieSessionMapper) {

        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @GetMapping("/by-user")
    public ShoppingCart getByUser(Authentication user) {
        return shoppingCartService.getByUser(userService.findByEmail(user));
    }

    @PostMapping("/movie-sessions")
    public void addMovieSession(Authentication user,
                                @RequestBody MovieSessionRequestDto movieSession) {
        shoppingCartService.addSession(movieSessionMapper.mapDtoToMovieSession(movieSession),
                userService.findByEmail(user));
    }
}

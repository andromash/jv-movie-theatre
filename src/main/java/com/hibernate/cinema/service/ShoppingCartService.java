package com.hibernate.cinema.service;

import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.User;

public interface ShoppingCartService {
    void addSession(MovieSession movieSession, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clear(ShoppingCart shoppingCart);
}

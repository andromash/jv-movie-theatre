package com.hibernate.cinema.dao;

import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.User;

public interface ShoppingCartDao {
    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart getByUser(User user);

    void update(ShoppingCart shoppingCart);
}

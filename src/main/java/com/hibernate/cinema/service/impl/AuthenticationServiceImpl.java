package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.exception.AuthenticationException;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.AuthenticationService;
import com.hibernate.cinema.service.ShoppingCartService;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.util.HashUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty() || isPasswordNotValid(password, userFromDB.get())) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return userFromDB.get();
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user = userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }

    private boolean isPasswordNotValid(String password, User userFromDB) {
        return !HashUtil.hashPassword(password, userFromDB.getSalt())
                .equals(userFromDB.getPassword());
    }
}

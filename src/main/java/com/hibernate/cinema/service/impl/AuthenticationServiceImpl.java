package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.exception.AuthenticationException;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.AuthenticationService;
import com.hibernate.cinema.service.ShoppingCartService;
import com.hibernate.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User register(String email, String password, String passwordRepeated) {
        if (isRepeatedPasswordWrong(password, passwordRepeated)) {
            throw new RuntimeException("Your password and repeated password don't match");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user = userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }

    private boolean isRepeatedPasswordWrong(String password, String passwordRepeated) {
        return !password.equals(passwordRepeated);
    }
}

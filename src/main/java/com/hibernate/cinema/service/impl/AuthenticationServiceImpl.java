package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.exception.AuthenticationException;
import com.hibernate.cinema.lib.Inject;
import com.hibernate.cinema.lib.Service;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.AuthenticationService;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

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
        return userService.add(user);
    }

    private boolean isPasswordNotValid(String password, User userFromDB) {
        return !HashUtil.hashPassword(password, userFromDB.getSalt())
                .equals(userFromDB.getPassword());
    }
}

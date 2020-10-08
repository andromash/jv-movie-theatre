package com.hibernate.cinema.service;

import com.hibernate.cinema.exception.AuthenticationException;
import com.hibernate.cinema.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;

    User register(String email, String password);
}

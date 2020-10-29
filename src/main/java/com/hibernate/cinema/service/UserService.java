package com.hibernate.cinema.service;

import com.hibernate.cinema.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User add(User user);

    User findByEmail(String email);

    User findUser(Authentication authentication);
}

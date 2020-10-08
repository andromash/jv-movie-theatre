package com.hibernate.cinema.service;

import com.hibernate.cinema.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    Optional<User> findByEmail(String email);
}
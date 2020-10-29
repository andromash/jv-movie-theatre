package com.hibernate.cinema.service;

import com.hibernate.cinema.model.User;

public interface UserService {
    User add(User user);

    User findByEmail(String email);
}

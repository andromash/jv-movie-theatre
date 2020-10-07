package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.dao.UserDao;
import com.hibernate.cinema.lib.Inject;
import com.hibernate.cinema.lib.Service;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.util.HashUtil;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        byte[] salt = HashUtil.getSalt();
        String password = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(password);
        user.setSalt(salt);
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}

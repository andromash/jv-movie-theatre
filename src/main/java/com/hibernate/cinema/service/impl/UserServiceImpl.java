package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.dao.UserDao;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User add(User user) {
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        user.setPassword(hashedPassword);
        return userDao.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).get();
    }
}

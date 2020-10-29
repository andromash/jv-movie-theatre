package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.dto.UserResponseDto;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/by-email")
    public UserResponseDto getUserByEmail(Authentication user) {
        return userMapper.mapUserToDto(userService.findByEmail(user.getName()));
    }
}

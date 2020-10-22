package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.User;
import com.hibernate.cinema.model.dto.UserRequestDto;
import com.hibernate.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void signUp(@RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto.getEmail().matches("^(.+)@(.+)$")
                && userRequestDto.getPassword().equals(userRequestDto.getPasswordRepeated())) {
            userService.add(castDtoToUser(userRequestDto));
        }
    }

    private User castDtoToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        return user;
    }
}

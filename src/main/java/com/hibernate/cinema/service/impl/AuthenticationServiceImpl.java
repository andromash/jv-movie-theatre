package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.AuthenticationService;
import com.hibernate.cinema.service.RoleService;
import com.hibernate.cinema.service.ShoppingCartService;
import com.hibernate.cinema.service.UserService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService,
                                     RoleService roleService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.roleService = roleService;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(roleService.getRoleByName("USER")));
        user = userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}

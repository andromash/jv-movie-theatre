package com.hibernate.cinema.service;

import com.hibernate.cinema.model.Role;
import com.hibernate.cinema.model.User;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InjectData {
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InjectData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initData() {
        roleService.add(Role.of(USER));
        roleService.add(Role.of(ADMIN));

        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setPassword("12345");
        admin.setRoles(Set.of(roleService.getRoleByName(ADMIN)));
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("1111");
        user.setRoles(Set.of(roleService.getRoleByName(USER)));

        userService.add(admin);
        userService.add(user);
    }

}

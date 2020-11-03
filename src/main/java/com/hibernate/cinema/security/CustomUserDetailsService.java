package com.hibernate.cinema.security;

import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(email);
            builder.password(user.getPassword());
            String[] roles = user.getRoles().stream()
                    .map(role -> role.getRoleName().toString())
                    .toArray(String[]::new);
            builder.roles(roles);
        } else {
            throw new UsernameNotFoundException("User with email " + email + "not found.");
        }

        return builder.build();
    }
}

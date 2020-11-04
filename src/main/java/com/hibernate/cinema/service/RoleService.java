package com.hibernate.cinema.service;

import com.hibernate.cinema.model.Role;

public interface RoleService {
    void add(Role role);

    Role getRoleByName(String roleName);
}

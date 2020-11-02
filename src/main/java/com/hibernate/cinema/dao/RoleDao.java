package com.hibernate.cinema.dao;

import com.hibernate.cinema.model.Role;

public interface RoleDao {
    void add(Role role);

    Role getRoleByName(String roleName);
}

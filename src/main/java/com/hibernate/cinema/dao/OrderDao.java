package com.hibernate.cinema.dao;

import com.hibernate.cinema.model.Order;
import com.hibernate.cinema.model.User;
import java.util.List;

public interface OrderDao {
    Order add(Order order);

    List<Order> getOrdersOfUser(User user);
}

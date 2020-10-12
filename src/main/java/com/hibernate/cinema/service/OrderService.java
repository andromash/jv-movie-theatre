package com.hibernate.cinema.service;

import com.hibernate.cinema.model.Order;
import com.hibernate.cinema.model.Ticket;
import com.hibernate.cinema.model.User;
import java.util.List;

public interface OrderService {
    Order completeOrder(List<Ticket> tickets, User user);

    List<Order> getOrderHistory(User user);
}

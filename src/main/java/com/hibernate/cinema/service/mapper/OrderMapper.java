package com.hibernate.cinema.service.mapper;

import com.hibernate.cinema.model.Order;
import com.hibernate.cinema.model.Ticket;
import com.hibernate.cinema.model.dto.OrderRequestDto;
import com.hibernate.cinema.model.dto.OrderResponseDto;
import com.hibernate.cinema.service.UserService;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private final UserService userService;

    @Autowired
    public OrderMapper(UserService userService) {
        this.userService = userService;
    }

    public Order castDtoToOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setOrderDate(orderRequestDto.getOrderDate());
        order.setUser(userService.findByEmail(orderRequestDto.getUserEmail()).get());
        order.setTickets(new ArrayList<>());
        return order;
    }

    public OrderResponseDto castOrderToDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setOrderDate(order.getOrderDate());
        orderResponseDto.setTickets(order.getTickets().stream()
                .map(Ticket::getId)
                .collect(Collectors.toList()));
        return orderResponseDto;
    }
}

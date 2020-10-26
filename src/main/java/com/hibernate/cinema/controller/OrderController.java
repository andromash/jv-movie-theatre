package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.dto.OrderResponseDto;
import com.hibernate.cinema.service.OrderService;
import com.hibernate.cinema.service.ShoppingCartService;
import com.hibernate.cinema.service.UserService;
import com.hibernate.cinema.service.mapper.OrderMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper,
                           UserService userService, ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/complete")
    public void completeOrder(@RequestBody String email) {
        ShoppingCart shoppingCart = shoppingCartService
                .getByUser(userService.findByEmail(email));
        orderService.completeOrder(shoppingCart.getTickets(), shoppingCart.getUser());
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistory(@RequestParam String email) {
        return orderService.getOrderHistory(userService.findByEmail(email)).stream()
                .map(orderMapper::castOrderToDto)
                .collect(Collectors.toList());
    }
}

package com.hibernate.cinema.controller;

import com.hibernate.cinema.model.Order;
import com.hibernate.cinema.model.dto.OrderRequestDto;
import com.hibernate.cinema.model.dto.OrderResponseDto;
import com.hibernate.cinema.service.OrderService;
import com.hibernate.cinema.service.mapper.OrderMapper;
import java.util.List;
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
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/complete")
    public void completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderMapper.castDtoToOrder(orderRequestDto);
        orderService.completeOrder(order.getTickets(), order.getUser());
    }

    @GetMapping
    public List<OrderResponseDto> getOrderHistory(@RequestParam Long userId) {
        return orderService.getOrderHistory();
    }
}

package com.hibernate.cinema.service.mapper;

import com.hibernate.cinema.model.Order;
import com.hibernate.cinema.model.dto.OrderResponseDto;
import com.hibernate.cinema.model.dto.TicketResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto mapOrderToDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setOrderDate(order.getOrderDate());
        List<TicketResponseDto> tickets = order.getTickets().stream()
                .map(ticket -> new TicketResponseDto(ticket.getId(),
                        ticket.getMovieSession().getMovie().getTitle()))
                .collect(Collectors.toList());
        orderResponseDto.setTickets(tickets);
        orderResponseDto.setUserEmail(order.getUser().getEmail());
        return orderResponseDto;
    }
}

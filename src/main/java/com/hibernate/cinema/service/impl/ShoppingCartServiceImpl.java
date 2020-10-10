package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.dao.ShoppingCartDao;
import com.hibernate.cinema.dao.TicketDao;
import com.hibernate.cinema.lib.Inject;
import com.hibernate.cinema.lib.Service;
import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.Ticket;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.service.ShoppingCartService;
import java.util.ArrayList;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;
    @Inject
    private TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        ShoppingCart usersShoppingCart = shoppingCartDao.getByUser(user);
        Ticket ticket = new Ticket();
        ticket.setMovieSession(movieSession);
        ticket.setUser(user);
        Ticket ticketFromDb = ticketDao.add(ticket);
        usersShoppingCart.getTickets().add(ticketFromDb);
        shoppingCartDao.update(usersShoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTickets(new ArrayList<>());
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setTickets(new ArrayList<>());
        shoppingCartDao.update(shoppingCart);
    }
}

package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.ShoppingCartDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.model.ShoppingCart;
import com.hibernate.cinema.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private static final Logger logger = Logger.getLogger(ShoppingCartDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        logger.info("Trying to add shopping cart");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            logger.info("Shopping cart successfully created: " + shoppingCart);
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add shopping cart "
                    + shoppingCart + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        logger.info("Trying to get shopping cart of user: " + user);
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ShoppingCart sc "
                    + "left join fetch sc.tickets "
                    + "join fetch sc.user "
                    + "where sc.user = :user", ShoppingCart.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get shopping cart of user: " + user, e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        logger.info("Trying to update shopping cart: " + shoppingCart);
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            logger.info("Shopping cart successfully updated");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update the shopping cart: "
                    + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

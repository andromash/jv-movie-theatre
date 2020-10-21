package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.OrderDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.model.Order;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order add(Order order) {
        logger.info("Trying to add order");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            logger.info("Order successfully added: " + order);
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create order: " + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getOrdersOfUser(User user) {
        logger.info("Trying to get orders of user: " + user);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select distinct o from Order o "
                    + "left join fetch o.tickets "
                    + "where o.user = :user", Order.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get orders of user: " + user, e);
        }
    }
}

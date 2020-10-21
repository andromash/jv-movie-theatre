package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.TicketDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.model.Ticket;
import com.hibernate.cinema.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDaoImpl implements TicketDao {
    private static final Logger logger = Logger.getLogger(TicketDaoImpl.class);

    @Override
    public Ticket add(Ticket ticket) {
        logger.info("Trying to add ticket");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            logger.info("Ticket successfully updated: " + ticket);
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add ticket " + ticket + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

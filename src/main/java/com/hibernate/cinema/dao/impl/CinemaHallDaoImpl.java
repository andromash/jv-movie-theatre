package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.CinemaHallDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.model.CinemaHall;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger logger = Logger.getLogger(CinemaHallDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        logger.info("Trying to add cinemaHall");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(cinemaHall);
            transaction.commit();
            logger.info("Cinema hall successfully added: " + cinemaHall);
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert cinema hall "
                    + cinemaHall + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        logger.info("Trying to get all cinema halls");
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> getAllCinemaHallsQuery =
                    session.createQuery("from CinemaHall", CinemaHall.class);
            return getAllCinemaHallsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get cinema halls from DB", e);
        }
    }
}

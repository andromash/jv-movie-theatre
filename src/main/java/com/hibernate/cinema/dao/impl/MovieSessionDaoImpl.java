package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.MovieSessionDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.lib.Dao;
import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger logger = Logger.getLogger(MovieSessionDaoImpl.class);

    @Override
    public MovieSession add(MovieSession movieSession) {
        logger.info("Trying to add movie session");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            logger.info("Movie session successfully created: " + movieSession);
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie session"
                    + movieSession + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        logger.info("Trying to get available sessions");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionsQuery =
                    session.createQuery("from MovieSession where movie_id = :id"
                                    + " and showTime between :start AND :end",
                            MovieSession.class);
            getAllAvailableSessionsQuery.setParameter("id", movieId);
            getAllAvailableSessionsQuery.setParameter("start", date.atStartOfDay());
            getAllAvailableSessionsQuery.setParameter("end", date.atTime(LocalTime.MAX));
            return getAllAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie sessions from DB by id = "
                    + movieId + " and date = " + date, e);
        }
    }
}

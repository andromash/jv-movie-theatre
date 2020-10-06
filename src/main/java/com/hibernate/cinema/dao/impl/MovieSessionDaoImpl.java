package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.MovieSessionDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.lib.Dao;
import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.MovieSession;
import com.hibernate.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
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
    public List<MovieSession> getAvailable(Movie movie, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllAvailableSessionsQuery =
                    session.createQuery("from MovieSession where movie = :movie "
                                    + "and showTime between :start AND :end",
                            MovieSession.class);
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            getAllAvailableSessionsQuery.setParameter("movie", movie);
            getAllAvailableSessionsQuery.setParameter("start", start);
            getAllAvailableSessionsQuery.setParameter("end", end);
            return getAllAvailableSessionsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie sessions from DB", e);
        }
    }
}


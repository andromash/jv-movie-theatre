package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.MovieDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.model.Movie;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDaoImpl implements MovieDao {
    private static final Logger logger = Logger.getLogger(MovieDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public MovieDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Movie add(Movie movie) {
        logger.info("Trying to add movie");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
            logger.info("Movie successfully added: " + movie);
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert movie " + movie + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Movie> getAll() {
        logger.info("Trying to get all movies");
        try (Session session = sessionFactory.openSession()) {
            Query<Movie> getAllMoviesQuery = session.createQuery("from Movie", Movie.class);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movies from DB", e);
        }
    }
}

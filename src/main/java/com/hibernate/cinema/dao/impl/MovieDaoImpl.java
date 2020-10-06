package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.MovieDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.lib.Dao;
import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieDaoImpl implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
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
    public Movie get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movie> getMovieByIdQuery = session.createQuery("from Movie "
                    + "where id = :id", Movie.class);
            getMovieByIdQuery.setParameter("id", id);
            return getMovieByIdQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movie from DB with id = " + id, e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movie> getAllMoviesQuery = session.createQuery("from Movie", Movie.class);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get movies from DB", e);
        }
    }
}

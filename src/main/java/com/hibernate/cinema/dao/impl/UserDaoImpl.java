package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.UserDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.lib.Dao;
import com.hibernate.cinema.model.User;
import com.hibernate.cinema.util.HibernateUtil;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User add(User user) {
        logger.info("Trying to add new user");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("User successfully created: " + user);
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add user " + user + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Trying to user by email: " + email);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get user with email: " + email, e);
        }
    }
}

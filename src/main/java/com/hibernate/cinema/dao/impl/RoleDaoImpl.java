package com.hibernate.cinema.dao.impl;

import com.hibernate.cinema.dao.RoleDao;
import com.hibernate.cinema.exception.DataProcessingException;
import com.hibernate.cinema.model.Role;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Role role) {
        logger.info("Trying to add new role");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            logger.info("Role successfully created: " + role);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add role " + role + "to database :", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Role getRoleByName(String roleName) {
        logger.info("Trying to get role by name " + roleName);
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Role where roleName = :name", Role.class)
                    .setParameter("name", roleName)
                    .getSingleResult();
        }
    }
}

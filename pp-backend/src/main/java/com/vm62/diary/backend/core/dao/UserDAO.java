package com.vm62.diary.backend.core.dao;

import com.vm62.diary.backend.core.entities.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class UserDAO {

    @Inject
    private Provider<EntityManager> emProvider;

    public User createUser(User user) {
        EntityManager em = emProvider.get();
        em.persist(user);
        em.flush();
        return user;
    }

    public User updateUser(User user) {
        EntityManager em = emProvider.get();
        User updatedUser = em.merge(user);
        em.flush();
        return updatedUser;
    }

    public User getUserById(Long id) {

        EntityManager em = emProvider.get();

        TypedQuery<User> query = em.createQuery("SELECT us FROM "
                + User.class.getName() + " us " + "WHERE us.id = :ID", User.class);

        query.setParameter("ID", id);

        return QueryHelper.getSingleValueOrNull(query);
    }

    public User getUserByEmail (String email){
        EntityManager em = emProvider.get();

        TypedQuery<User> query = em.createQuery("SELECT us FROM "
                + User.class.getName() + " us " + "WHERE us.email = :EMAIL", User.class);

        query.setParameter("EMAIL", email);

        return QueryHelper.getSingleValueOrNull(query);
    }


    public User getUserByRegistrationId (String registrationId){
        EntityManager em = emProvider.get();

        TypedQuery<User> query = em.createQuery("SELECT us FROM "
                + User.class.getName() + " us " + "WHERE us.registrationId = :REGISTRATION_ID", User.class);

        query.setParameter("REGISTRATION_ID", registrationId);

        return QueryHelper.getSingleValueOrNull(query);
    }

    public List<User> getUsersByRegistrationStatus (Boolean registrationStatus){
        EntityManager em = emProvider.get();

        TypedQuery<User> query = em.createQuery("SELECT us FROM "
                + User.class.getName() + " us " + "WHERE us.isRegister = :REGISTRATION_APPROVED", User.class);

        query.setParameter("REGISTRATION_APPROVED", registrationStatus);

        return query.getResultList();
    }

    public boolean isUserEmailExists(String email){
        EntityManager em = emProvider.get();

        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u.id) FROM " + User.class.getName() + " u " +
                "WHERE u.email=:EMAIL", Long.class);

        query.setParameter("EMAIL", email);
        return query.getSingleResult() > 0;
    }


}

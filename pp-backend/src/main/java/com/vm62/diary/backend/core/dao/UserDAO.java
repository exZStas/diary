package com.vm62.diary.backend.core.dao;

import com.vm62.diary.backend.core.entities.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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


}

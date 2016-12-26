package com.vm62.diary.backend.core.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.entities.Admin;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Transactional
public class AdminDAO {
    @Inject
    private Provider<EntityManager> emProvider;

    public Admin getAdmin(){
        EntityManager em = emProvider.get();

        TypedQuery<Admin> query = em.createQuery("SELECT adm FROM " +
        Admin.class.getName() + " adm ", Admin.class);

        return QueryHelper.getSingleValueOrNull(query);
    }
}

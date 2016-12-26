package com.vm62.diary.backend.core.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vm62.diary.backend.core.entities.Category;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class CategoryDAO {

    @Inject
    private Provider<EntityManager> emProvider;

    public void createCategory(Category category) throws ServiceException {
        EntityManager em = emProvider.get();



    }
}

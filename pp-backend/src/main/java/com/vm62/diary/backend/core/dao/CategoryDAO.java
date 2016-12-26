package com.vm62.diary.backend.core.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vm62.diary.backend.core.entities.Category;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional
public class CategoryDAO {

    @Inject
    private Provider<EntityManager> emProvider;

    public void createCategory(Category category) throws ServiceException {
        EntityManager em = emProvider.get();

        em.persist(category);
        em.flush();
    }

    public Category getCategoryByName(String categoryName){
        EntityManager em = emProvider.get();

        TypedQuery<Category> query = em.createQuery("SELECT cat FROM " + Category.class.getName() + " cat " +
        "WHERE cat.categoryName = :CATEGORY_NAME", Category.class);

        query.setParameter("CATEGORY_NAME", categoryName);

        return query.getSingleResult();
    }

    public Category getCategoryByColor(String categoryColor){
        EntityManager em = emProvider.get();

        TypedQuery<Category> query = em.createQuery("SELECT cat FROM " + Category.class.getName() + " cat " +
                "WHERE cat.categoryColor = :CATEGORY_COLOR", Category.class);

        query.setParameter("CATEGORY_COLOR", categoryColor);

        return query.getSingleResult();
    }
}

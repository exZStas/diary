package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.CategoryDAO;
import com.vm62.diary.backend.core.entities.Category;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ValidationUtils;

@Transactional(rollbackOn = {ServiceException.class}, ignore = {RuntimeException.class})
public class CategoryBean {

    @Inject
    private CategoryDAO categoryDAO;

    public void createCategory(String categoryName, String categoryColor) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(categoryName, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "name");
        ValidationUtils.ifNullOrEmpty(categoryColor, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "color");

        Category category = new Category(categoryName, categoryColor);
        categoryDAO.createCategory(category);
    }

    public Category getCategoryByName(String categoryName) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(categoryName, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "name");

        return categoryDAO.getCategoryByName(categoryName);
    }

    public Category getCategoryByColor(String categoryColor) throws ServiceException {
        ValidationUtils.ifNullOrEmpty(categoryColor, ErrorType.CANNOT_ALL_BE_NULL_OR_EMPTY, "color");

        return categoryDAO.getCategoryByColor(categoryColor);

    }
}

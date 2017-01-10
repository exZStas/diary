package com.vm62.diary.backend.core.bean;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.vm62.diary.backend.core.dao.CategoryDAO;
import com.vm62.diary.backend.core.entities.Category;
import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

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

    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();

        //add default categories
        for (com.vm62.diary.common.constants.Category category : com.vm62.diary.common.constants.Category.values()){
            categories.add(new Category(category.getCategory(), category.getColor()));
        }

        List<Category> categoriesFromDB = categoryDAO.getAllCategories();

        categories.addAll(categoriesFromDB);

        return categories;
    }
}

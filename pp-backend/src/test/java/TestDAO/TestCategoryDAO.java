package TestDAO;

import TestRunner.TestRunner;
import com.google.inject.Inject;
import com.vm62.diary.backend.core.dao.CategoryDAO;
import com.vm62.diary.backend.core.entities.Category;
import factory.CategoryFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestRunner.class)
public class TestCategoryDAO {

    @Inject
    private CategoryDAO categoryDAO;
    @Inject
    private CategoryFactory categoryFactory;


    private Category category1;
    private String name1 = "Sport";
    private String color1 = "red";

    @Before
    public void setUp(){
        category1 = categoryFactory.createCategory(name1, color1);
    }


    @Test
    public void getAllCategories(){
        List<Category> categoryList = categoryDAO.getAllCategories();
        assertEquals(categoryList.get(0).getCategoryName(), name1);
    }
}

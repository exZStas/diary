package factory;

import com.google.inject.Inject;
import com.vm62.diary.backend.core.entities.Category;

import javax.persistence.EntityManager;

public class CategoryFactory {
    @Inject
    private EntityManager em;

    public Category createCategory(String categoryName, String categoryColor){
        Category category = new Category(categoryName, categoryColor);

        em.persist(category);
        em.flush();
        return category;
    }
}

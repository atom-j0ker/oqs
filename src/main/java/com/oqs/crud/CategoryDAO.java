package com.oqs.crud;

import com.oqs.model.Category;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class CategoryDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Category category) {
        entityManager.merge(category);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Category.class, id));
    }

    public Category get(long id) {
        return entityManager.find(Category.class, id);
    }

    public List<Category> getCategories() {
        TypedQuery<Category> query = entityManager.createQuery(
                "select c from Category c where c.category is null", Category.class
        );
        List<Category> result = query.getResultList();
        return result;
    }

    public List<Category> getSubcategories(long categoryId) {
        TypedQuery<Category> query = entityManager.createQuery(
                "select c from Category c where c.category.id = :categoryId", Category.class
        ).setParameter("categoryId", categoryId);
        List<Category> result = query.getResultList();
        return result;
    }

}

package com.oqs.dao;

import com.oqs.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDao {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(Category category) {
        entityManager.merge(category);
    }

    @Transactional
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
        return query.getResultList();
    }

    public List<Category> getSubcategories(long categoryId) {
        TypedQuery<Category> query = entityManager.createQuery(
                "select c from Category c where c.category.id = :categoryId", Category.class
        ).setParameter("categoryId", categoryId);
        return query.getResultList();
    }

}

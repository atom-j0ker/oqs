package com.oqs.crud;

import com.oqs.model.Category;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}

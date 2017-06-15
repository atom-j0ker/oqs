package com.oqs.crud;

import com.oqs.model.Rating;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class RatingDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Rating rating) {
        entityManager.merge(rating);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Rating.class, id));
    }

    public Rating get(long id) {
        return entityManager.find(Rating.class, id);
    }
}

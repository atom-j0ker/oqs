package com.oqs.crud;

import com.oqs.model.Rating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RatingDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(Rating rating) {
        entityManager.merge(rating);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Rating.class, id));
    }

    public Rating get(long id) {
        return entityManager.find(Rating.class, id);
    }

    public double getRating(long businessId) {
        TypedQuery<Rating> query = entityManager.createQuery(
                "select r from Rating r where r.business=" + businessId, Rating.class
        );
        List<Rating> result = query.getResultList();
        double rating = 0;
        for (Rating r : result)
            rating += r.getRating();
        if (!result.isEmpty())
            rating = rating / result.size();
        return rating;
    }
}

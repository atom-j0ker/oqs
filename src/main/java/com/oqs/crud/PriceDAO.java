package com.oqs.crud;

import com.oqs.model.Price;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class PriceDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Price price) {
        entityManager.merge(price);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Price.class, id));
    }

    public Price get(long id) {
        return entityManager.find(Price.class, id);
    }
}

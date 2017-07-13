package com.oqs.crud;

import com.oqs.model.Price;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PriceDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(Price price) {
        entityManager.merge(price);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Price.class, id));
    }

    public Price get(long id) {
        return entityManager.find(Price.class, id);
    }
}

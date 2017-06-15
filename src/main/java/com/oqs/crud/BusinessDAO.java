package com.oqs.crud;

import com.oqs.model.Business;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class BusinessDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Business business) {
        entityManager.merge(business);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Business.class, id));
    }

    public Business get(long id) {
        return entityManager.find(Business.class, id);
    }

}

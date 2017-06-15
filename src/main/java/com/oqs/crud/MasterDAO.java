package com.oqs.crud;

import com.oqs.model.Master;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class MasterDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Master master) {
        entityManager.merge(master);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Master.class, id));
    }

    public Master get(long id) {
        return entityManager.find(Master.class, id);
    }
}

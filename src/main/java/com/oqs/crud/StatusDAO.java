package com.oqs.crud;

import com.oqs.model.Status;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class StatusDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Status status) {
        entityManager.merge(status);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Status.class, id));
    }

    public Status get(long id) {
        return entityManager.find(Status.class, id);
    }
}

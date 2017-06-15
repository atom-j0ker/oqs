package com.oqs.crud;

import com.oqs.model.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class ServiceDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Service service) {
        entityManager.merge(service);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Service.class, id));
    }

    public Service get(long id) {
        return entityManager.find(Service.class, id);
    }
}

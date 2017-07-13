package com.oqs.crud;

import com.oqs.model.Photo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PhotoDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public long saveOrUpdate(Photo photo) {
        return entityManager.merge(photo).getId();
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Photo.class, id));
    }

    public Photo get(long id) {
        return entityManager.find(Photo.class, id);
    }
}

package com.oqs.crud;

import com.oqs.model.Photo;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class PhotoDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public long saveOrUpdate(Photo photo) {
        return entityManager.merge(photo).getId();
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Photo.class, id));
    }

    public Photo get(long id) {
        return entityManager.find(Photo.class, id);
    }
}

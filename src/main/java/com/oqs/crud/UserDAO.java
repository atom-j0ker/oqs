package com.oqs.crud;

import com.oqs.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UserDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(User.class, id));
    }

    public User get(long id) {
        return entityManager.find(User.class, id);
    }

    public long getId(String email) {
        Query query = entityManager.createQuery(
                "select u.id from User u where u.email= :email").setParameter("email", email);
        return (Long)query.getSingleResult();
    }
}

package com.oqs.dao;

import com.oqs.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class UserDao {
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

    public User get(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u where u.email = :email", User.class
        ).setParameter("email", email);
        return query.getSingleResult();
    }

    public long getId(String email) {
        Query query = entityManager.createQuery(
                "select u.id from User u where u.email= :email").setParameter("email", email);
        return (Long)query.getSingleResult();
    }
}

package com.oqs.crud;

import com.oqs.model.User;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional
public class UserDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(User user) {
        entityManager.merge(user);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(User.class, id));
    }

    public User get(long id) {
        return entityManager.find(User.class, id);
    }

    public long getId(String email) {
        Query query = entityManager.createQuery(
                "select u.id from User u where u.email= :email").setParameter("email", email);
        long result = (Long)query.getSingleResult();
        return result;
    }
}

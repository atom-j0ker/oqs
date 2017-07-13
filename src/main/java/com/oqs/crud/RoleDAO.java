package com.oqs.crud;

import com.oqs.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RoleDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public Role get(long id) {
        return entityManager.find(Role.class, id);
    }

    public long getId(String role) {
        Query query = entityManager.createQuery(
                "select r.id from Role r where r.role= :role").setParameter("role", role);
        long result = (Long)query.getSingleResult();
        return result;
    }
}

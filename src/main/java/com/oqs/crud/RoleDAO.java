package com.oqs.crud;

import com.oqs.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RoleDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public Role get(long id) {
        return entityManager.find(Role.class, id);
    }
}

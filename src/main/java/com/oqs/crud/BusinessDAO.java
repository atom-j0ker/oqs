package com.oqs.crud;

import com.oqs.model.Business;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BusinessDAO {//TODO check singleton annotation (how many instances)
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(Business business) {
        entityManager.merge(business);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Business.class, id));
    }

    public Business get(long id) {
        return entityManager.find(Business.class, id);
    }

    public List<Business> getBsnList() {
        TypedQuery<Business> query = entityManager.createQuery(
                "select b from Business b", Business.class
        );
        List<Business> result = query.getResultList();
        return result;
    }

    public List<Business> getBsnListByCategory(Long categoryId) {
        TypedQuery<Business> query = entityManager.createQuery(
                "select distinct b from Business b, Service s, Category c " +
                        "where b.id = s.business.id and s.category.id = c.id " +
                        "and (c.id = " + categoryId + " or c.category.id = " + categoryId + ") order by b.name", Business.class);
        List<Business> result = query.getResultList();
        return result;
    }
}

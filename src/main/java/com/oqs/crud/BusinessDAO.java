package com.oqs.crud;

import com.oqs.model.Business;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class BusinessDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Business business) {
        entityManager.merge(business);
    }

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

    //переделать через сервис
    public List<Business> getBsnListByCategory(Long categoryId) {
        TypedQuery<Business> query = entityManager.createQuery("select b from Business b where b.=" + categoryId + " order by b.name", Business.class);
        List<Business> result = query.getResultList();
        return result;
    }
}

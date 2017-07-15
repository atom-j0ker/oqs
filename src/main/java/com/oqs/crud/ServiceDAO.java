package com.oqs.crud;

import com.oqs.model.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ServiceDAO {
    @PersistenceContext//TODO read
    public EntityManager entityManager;

    @Transactional
    public long saveOrUpdate(Service service) {
        return entityManager.merge(service).getId();
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Service.class, id));
    }

    public Service get(long id) {
        return entityManager.find(Service.class, id);
    }

    public List<Service> getServiceListByOrganization(long organizationId) {
        TypedQuery<Service> query = entityManager.createQuery(
                "select s from Service s where s.business.id = :business", Service.class
        ).setParameter("business", organizationId);
        List<Service> result = query.getResultList();
        return result;
    }

    public List<Service> getServiceListByParams(String sortBy, String categoryId, String string) {
        String categorySearch = "1=1";
        String stringSearch = "1=1";
        if(!categoryId.equals("undefined"))
            categorySearch = "(s.category.id in " +
                    "(select c.id from Category c where c.category.id = " + Long.valueOf(categoryId) + ") or " +
                    "s.category.id = " + Long.valueOf(categoryId) + ")";
        if(!string.equals("undefined"))
            stringSearch = "s.name like '%" + string + "%'";
        TypedQuery<Service> query = entityManager.createQuery(
                "select s from Service s where " + categorySearch + " and " + stringSearch +
                        " order by " + sortBy, Service.class);
        List<Service> result = query.getResultList();
        return result;
    }
}

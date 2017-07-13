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

    public List<Service> getServiceListByCategory(long categoryId) {
        TypedQuery<Service> query = entityManager.createQuery(
                "select s from Service s where s.category.id in " +
                        "(select c.id from Category c where c.category.id = :categoryId) or s.category.id = :categoryId", Service.class
        ).setParameter("categoryId", categoryId);
        List<Service> result = query.getResultList();
        return result;
    }

    public List<Service> getServiceListByString(String string) {
        TypedQuery<Service> query = entityManager.createQuery(
                "select s from Service s where s.name like '%" + string + "%'", Service.class
        );
        List<Service> result = query.getResultList();
        return result;
    }
}

package com.oqs.crud;

import com.oqs.model.Master;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MasterDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(Master master) {
        entityManager.merge(master);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Master.class, id));
    }

    public Master get(long id) {
        return entityManager.find(Master.class, id);
    }

    public List<Master> getMasterListByOrganization(long organizationId) {
        TypedQuery<Master> query = entityManager.createQuery(
                "select m from Master m where m.business='" + organizationId + "'", Master.class
        );
        return query.getResultList();
    }

    public List<Master> getMasterListByServiceAndOrganization(long serviceId, long businessId) {
        Query query = entityManager.createNativeQuery("select m.* from master m, service_master sm  " +
                "where sm.sm_service = " + serviceId + " and m.master_id = sm.sm_master and m.master_business=" + businessId, Master.class);
        return query.getResultList();
    }
}

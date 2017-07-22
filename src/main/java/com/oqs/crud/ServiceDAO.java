package com.oqs.crud;

import com.oqs.pair.Pair;
import com.oqs.model.Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
        return query.getResultList();
    }

    public List<Service> getServiceListByMaster(long masterId) {
        Query query = entityManager.createNativeQuery("select s.* from service s, service_master sm " +
                "where s.service_id = sm.sm_service and sm.sm_master=" + masterId, Service.class);
        return query.getResultList();
    }

    public Pair<Integer, List<Service>> getServiceListByParams(String string, String categoryId, String sortBy,
                                                               int page, int rowsOnPage) {
        String categorySearch = "1=1";
        String stringSearch = "1=1";
        if (!categoryId.equals("undefined"))
            categorySearch = "(s.category.id in " +
                    "(select c.id from Category c where c.category.id = " + Long.valueOf(categoryId) + ") or " +
                    "s.category.id = " + Long.valueOf(categoryId) + ")";
        if (!string.equals("undefined"))
            stringSearch = "s.name like '%" + string + "%'";
        TypedQuery<Service> query = entityManager.createQuery(
                "select s from Service s where " + categorySearch + " and " + stringSearch +
                        " order by " + sortBy, Service.class);
        List<Service> result = query.getResultList();
        List<Service> serviceList = new ArrayList<Service>();
        Pair<Integer, List<Service>> pair = new Pair<Integer, List<Service>>(result.size(), null);
        ListIterator<Service> listIterator = result.listIterator(page * rowsOnPage - rowsOnPage);
        for (int i = 0; i < rowsOnPage; i++) {
            if (listIterator.hasNext())
                serviceList.add(listIterator.next());
            else
                break;
        }
        pair.setSecond(serviceList);
        return pair;
    }
}
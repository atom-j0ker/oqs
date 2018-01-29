package com.oqs.dao;

import com.oqs.model.Business;
import com.oqs.util.Pair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Repository
public class BusinessDao {//TODO check singleton annotation (how many instances)
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
        return query.getResultList();
    }

    public Pair<Integer, List<Business>> getBsnListByCategory(long categoryId, int page, int rowsOnPage) {
        TypedQuery<Business> query = entityManager.createQuery(
                "select distinct b from Business b, Service s, Category c " +
                        "where b.id = s.business.id and s.category.id = c.id " +
                        "and (c.id = " + categoryId + " or c.category.id = " + categoryId + ") order by b.name", Business.class);
        List<Business> result = query.getResultList();
        List<Business> businessList = new ArrayList<Business>();
        Pair<Integer, List<Business>> pair = new Pair<Integer, List<Business>>(result.size(), null);
        ListIterator<Business> listIterator = result.listIterator(page * rowsOnPage - rowsOnPage);
        for (int i = 0; i < rowsOnPage; i++) {
            if (listIterator.hasNext()) businessList.add(listIterator.next());
            else break;
        }
        pair.setSecond(businessList);
        return pair;
    }
}

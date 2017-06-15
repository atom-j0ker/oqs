package com.oqs.crud;

import com.oqs.model.Schedule;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class ScheduleDAO {
    @PersistenceContext
    public EntityManager entityManager;

    public void saveOrUpdate(Schedule schedule) {
        entityManager.merge(schedule);
    }

    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Schedule.class, id));
    }

    public Schedule get(long id) {
        return entityManager.find(Schedule.class, id);
    }
}

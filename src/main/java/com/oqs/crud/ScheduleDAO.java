package com.oqs.crud;

import com.oqs.model.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class ScheduleDAO {
    @PersistenceContext
    public EntityManager entityManager;

    @Transactional
    public void saveOrUpdate(Schedule schedule) {
        entityManager.merge(schedule);
    }

    @Transactional
    public void delete(long id) {
        entityManager.remove(entityManager.getReference(Schedule.class, id));
    }

    public Schedule get(long id) {
        return entityManager.find(Schedule.class, id);
    }

    public List<Schedule> getScheduleListByUser(long userId) {
        TypedQuery<Schedule> query = entityManager.createQuery(
                "select s from Schedule s where s.user.id=" + userId + " order by s.date, s.time", Schedule.class
        );
        return query.getResultList();
    }

    public List<Schedule> getScheduleListByBusiness(long businessId) {
        TypedQuery<Schedule> query = entityManager.createQuery(
                "select s from Schedule s where s.service.business.id=" + businessId + " order by s.date, s.time", Schedule.class
        );
        return query.getResultList();
    }

    public List<Schedule> getScheduleListByDateAndMaster(Date date, String masterId) {
        String dateSearch = "1=1";
        String masterSearch = "1=1";
        if (date != null)
            dateSearch = "s.date='" + date + "'";
        if (!masterId.equals("undefined"))
            masterSearch = "s.master.id=" + masterId;
        TypedQuery<Schedule> query = entityManager.createQuery(
                "select s from Schedule s where " + dateSearch + " and " + masterSearch +
                        " order by s.date, s.time", Schedule.class);
        return query.getResultList();
    }

    public List<Time> getTimeListBusy(long masterId, Date date) {
        Query query = entityManager.createQuery(
                "select s.time from Schedule s where s.master.id=" + masterId + " and s.date='" + date + "'");
        return query.getResultList();
    }
}

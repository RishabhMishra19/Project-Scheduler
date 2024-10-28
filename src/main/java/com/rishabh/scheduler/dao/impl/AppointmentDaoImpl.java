package com.rishabh.scheduler.dao.impl;

import com.rishabh.scheduler.dao.AppointmentDao;
import com.rishabh.scheduler.dao.entity.Appointment;
import com.rishabh.scheduler.dao.entity.Operator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Appointment> getScheduledAppointments(Long operatorId, LocalDateTime startTime, LocalDateTime endTime) {
        Assert.notNull(operatorId, "'operatorId' is required");
        Assert.notNull(startTime, "'startTime' is required");
        Assert.notNull(endTime, "'endTime' is required");

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);
        query.select(root).where(
                builder.equal(root.get(Appointment.OPERATOR_FIELD_NAME), new Operator(operatorId)),
                builder.greaterThanOrEqualTo(root.get(Appointment.START_TIME_FIELD_NAME), startTime),
                builder.lessThanOrEqualTo(root.get(Appointment.END_TIME_FIELD_NAME), endTime)
        );
        return em.createQuery(query).getResultList();
    }

    @Override
    public Appointment save(Appointment appointment) {
        Assert.notNull(appointment, "'appointment' is required");

        em.persist(appointment);
        return appointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        Assert.notNull(appointment, "'appointment' is required");

        em.merge(appointment);
        return appointment;
    }

    @Override
    public void delete(Appointment appointment) {
        Assert.notNull(appointment, "'appointment' is required");

        em.remove(appointment);
    }

    @Override
    public <T> T getById(Long id, Class<T> tClass) {
        return em.find(tClass, id);
    }

}

package com.rishabh.scheduler.dao;

import com.rishabh.scheduler.dao.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDao  {

    List<Appointment> getScheduledAppointments(Long operatorId, LocalDateTime startTime, LocalDateTime endTime);
    Appointment save(Appointment appointment);
    Appointment update(Appointment appointment);
    void delete(Appointment appointment);
    <T> T getById(Long id, Class<T> tClass);

}

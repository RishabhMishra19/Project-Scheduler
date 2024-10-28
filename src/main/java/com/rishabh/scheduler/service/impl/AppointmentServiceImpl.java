package com.rishabh.scheduler.service.impl;

import com.rishabh.scheduler.dao.AppointmentDao;
import com.rishabh.scheduler.dao.entity.Appointment;
import com.rishabh.scheduler.dao.entity.Customer;
import com.rishabh.scheduler.dao.entity.Operator;
import com.rishabh.scheduler.service.AppointmentService;
import com.rishabh.scheduler.service.vo.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public AppointmentResponseVo scheduleAppointment(@Valid ScheduleAppointmentRequestVo requestVo) {
        Operator operator = appointmentDao.getById(requestVo.getOperatorId(), Operator.class);
        if (operator == null) {
            throw new RuntimeException("Invalid Operator!");
        }
        Customer customer = appointmentDao.getById(requestVo.getOperatorId(), Customer.class);
        if (customer == null) {
            throw new RuntimeException("Invalid Customer!");
        }
        Appointment appointment = appointmentDao.save(
                Appointment.builder()
                        .customer(customer)
                        .operator(operator)
                        .startTime(requestVo.getStartTime())
                        .endTime(requestVo.getEndTime())
                        .build()
        );
        return new AppointmentResponseVo(appointment);
    }

    @Override
    public AppointmentResponseVo rescheduleAppointment(@Valid RescheduleAppointmentRequestVo requestVo) {
        Appointment appointment = appointmentDao.getById(requestVo.getAppointmentId(), Appointment.class);
        if (appointment == null) {
            throw new RuntimeException("Invalid Appointment!");
        }
        appointment.setStartTime(requestVo.getStartTime());
        appointment.setEndTime(requestVo.getEndTime());
        appointmentDao.update(appointment);
        return new AppointmentResponseVo(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentDao.getById(appointmentId, Appointment.class);
        if (appointment == null) {
            throw new RuntimeException("Invalid Appointment!");
        }
        appointmentDao.delete(appointment);
    }

    @Override
    public List<AppointmentResponseVo> getScheduledAppointments(@Valid GetScheduledAppointmentsRequestVo requestVo) {
        Operator operator = appointmentDao.getById(requestVo.getOperatorId(), Operator.class);
        if (operator == null) {
            throw new RuntimeException("Invalid Operator!");
        }
        List<Appointment> appointments = appointmentDao.getScheduledAppointments(
                requestVo.getOperatorId(), requestVo.getStartOfDay(), requestVo.getEndOfDay()
        );
        if (appointments == null) {
            return new ArrayList<>();
        }
        return appointments.stream().map(AppointmentResponseVo::new).toList();
    }

    @Override
    public GetAppointmentSlotsResponseVo getAppointmentSlots(@Valid GetAppointmentSlotsRequestVo requestVo) {
        Operator operator = appointmentDao.getById(requestVo.getOperatorId(), Operator.class);
        if (operator == null) {
            throw new RuntimeException("Invalid Operator!");
        }
        List<Appointment> appointments = appointmentDao.getScheduledAppointments(
                requestVo.getOperatorId(), requestVo.getStartOfDay(), requestVo.getEndOfDay()
        );
        return new GetAppointmentSlotsResponseVo(requestVo.getOperatorId(), appointments);
    }
}

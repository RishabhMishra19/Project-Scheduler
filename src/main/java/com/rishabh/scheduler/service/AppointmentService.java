package com.rishabh.scheduler.service;

import com.rishabh.scheduler.service.vo.*;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseVo scheduleAppointment(ScheduleAppointmentRequestVo requestVo);

    AppointmentResponseVo rescheduleAppointment(RescheduleAppointmentRequestVo requestVo);

    void deleteAppointment(Long appointmentId);

    List<AppointmentResponseVo> getScheduledAppointments(GetScheduledAppointmentsRequestVo requestVo);

    GetAppointmentSlotsResponseVo getAppointmentSlots(GetAppointmentSlotsRequestVo requestVo);

}

package com.rishabh.scheduler.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rishabh.scheduler.dao.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class AppointmentResponseVo {

    private Long appointmentId;
    private Long operatorId;
    private String operatorName;
    private Long customerId;
    private String customerName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public AppointmentResponseVo(Appointment appointment) {
        this.appointmentId = appointment.getId();
        this.operatorId = appointment.getOperator().getId();
        this.operatorName = appointment.getOperator().getName();
        this.customerId = appointment.getCustomer().getId();
        this.customerName = appointment.getCustomer().getName();
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
    }
}

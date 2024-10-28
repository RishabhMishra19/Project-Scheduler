package com.rishabh.scheduler.controller;

import com.rishabh.scheduler.service.AppointmentService;
import com.rishabh.scheduler.service.vo.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("schedule")
    public ResponseEntity<AppointmentResponseVo> scheduleAppointment(@RequestBody ScheduleAppointmentRequestVo requestVo) {
        AppointmentResponseVo responseVo = appointmentService.scheduleAppointment(requestVo);
        return new ResponseEntity<>(responseVo, this.getHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("reschedule")
    public ResponseEntity<AppointmentResponseVo> rescheduleAppointment(@RequestBody RescheduleAppointmentRequestVo requestVo) {
        AppointmentResponseVo responseVo = appointmentService.rescheduleAppointment(requestVo);
        return new ResponseEntity<>(responseVo, this.getHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("delete/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseVo>> getAppointments(@ModelAttribute @Valid GetScheduledAppointmentsRequestVo requestVo) {
        List<AppointmentResponseVo> response = appointmentService.getScheduledAppointments(requestVo);
        return new ResponseEntity<>(response, this.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("slots")
    public ResponseEntity<GetAppointmentSlotsResponseVo> getAppointmentSlots(@ModelAttribute @Valid GetAppointmentSlotsRequestVo requestVo) {
        GetAppointmentSlotsResponseVo response = appointmentService.getAppointmentSlots(requestVo);
        return new ResponseEntity<>(response, this.getHeaders(), HttpStatus.OK);
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("Accept", "application/json");
        return headers;
    }
}

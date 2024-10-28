package com.rishabh.scheduler.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rishabh.scheduler.dao.entity.Appointment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
public class GetAppointmentSlotsResponseVo {

    private Long operatorId;
    private List<DayWiseSlotsVo> dayWiseSlots;

    @AllArgsConstructor
    @Builder
    @Getter
    public static class DayWiseSlotsVo {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date date;
        private List<TimeSlotVo> slots;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class TimeSlotVo {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime startTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime endTime;
        private boolean isBooked;
    }

    public GetAppointmentSlotsResponseVo(Long operatorId, List<Appointment> appointments) {
        Map<LocalDate, List<Appointment>> appointmentsByDate = new HashMap<>();
        for (Appointment appointment : appointments) {
            LocalDate date = appointment.getStartTime().toLocalDate();
            appointmentsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(appointment);
        }

        List<GetAppointmentSlotsResponseVo.DayWiseSlotsVo> dayWiseSlots = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Appointment>> entry : appointmentsByDate.entrySet()) {
            LocalDate localDate = entry.getKey();
            List<Appointment> dateAppointments = entry.getValue();
            List<GetAppointmentSlotsResponseVo.TimeSlotVo> timeSlots = generateTimeSlots(dateAppointments);

            dayWiseSlots.add(
                    DayWiseSlotsVo.builder()
                            .date(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                            .slots(timeSlots)
                            .build()
            );
        }

        this.operatorId = operatorId;
        this.dayWiseSlots = dayWiseSlots;

    }

    private List<GetAppointmentSlotsResponseVo.TimeSlotVo> generateTimeSlots(List<Appointment> appointments) {
        List<GetAppointmentSlotsResponseVo.TimeSlotVo> timeSlots = new ArrayList<>();

        LocalTime dayStart = LocalTime.of(0, 0);
        LocalTime dayEnd = LocalTime.of(23, 59);

        List<Appointment> sortedAppointments = appointments.stream()
                .sorted(Comparator
                        .comparing(Appointment::getStartTime)
                        .thenComparing(Appointment::getEndTime)
                )
                .toList();

        LocalTime lastEndTime = dayStart;
        for (Appointment sortedAppointment : sortedAppointments) {
            if (sortedAppointment.getStartTime().toLocalTime().isAfter(lastEndTime)) {
                timeSlots.add(new GetAppointmentSlotsResponseVo.TimeSlotVo(lastEndTime, sortedAppointment.getStartTime().toLocalTime(), false));
                timeSlots.add(new GetAppointmentSlotsResponseVo.TimeSlotVo(sortedAppointment.getStartTime().toLocalTime(), sortedAppointment.getEndTime().toLocalTime(), true));
                lastEndTime = sortedAppointment.getEndTime().toLocalTime();
            } else {
                if (timeSlots.isEmpty()) {
                    timeSlots.add(new GetAppointmentSlotsResponseVo.TimeSlotVo(sortedAppointment.getStartTime().toLocalTime(), sortedAppointment.getEndTime().toLocalTime(), true));
                } else if (timeSlots.get(timeSlots.size()-1).isBooked()) {
                    timeSlots.get(timeSlots.size()-1).setEndTime(sortedAppointment.getEndTime().toLocalTime());
                } else {
                    timeSlots.add(new GetAppointmentSlotsResponseVo.TimeSlotVo(sortedAppointment.getStartTime().toLocalTime(), sortedAppointment.getEndTime().toLocalTime(), true));
                }
                lastEndTime = sortedAppointment.getEndTime().toLocalTime();
            }
        }

        if (timeSlots.isEmpty() || lastEndTime.isBefore(dayEnd)) {
            timeSlots.add(new GetAppointmentSlotsResponseVo.TimeSlotVo(lastEndTime, dayEnd, false));
        }

        return timeSlots;
    }

}

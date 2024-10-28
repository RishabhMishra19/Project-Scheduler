package com.rishabh.scheduler.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RescheduleAppointmentRequestVo {

    @NotNull(message = "'Appointment Id' is required")
    private Long appointmentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "'Start Time' is required")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "'End Time' is required")
    private LocalDateTime endTime;

    @AssertTrue(message = "'End Time' should be greater than 'Start Time'")
    boolean isEndTimeGreaterThanStartTime() {
        if (startTime == null || endTime == null) {
            return true; //already @NotNull validation error will be returned
        }
        return endTime.isAfter(startTime);
    }

}

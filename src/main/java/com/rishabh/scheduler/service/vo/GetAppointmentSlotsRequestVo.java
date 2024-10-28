package com.rishabh.scheduler.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Getter
public class GetAppointmentSlotsRequestVo {

    @NotNull(message = "'Operator Id' is required")
    private Long operatorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "'From Date' is required")
    private Date fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "'To Date' is required")
    private Date toDate;

    @AssertTrue(message = "'End Time' should be greater than 'Start Time'")
    boolean isToDateGreaterThanEqualToFromDate() {
        if (fromDate == null || toDate == null) {
            return true; //already @NotNull validation error will be returned
        }
        return fromDate.compareTo(toDate) <= 0;
    }

    public LocalDateTime getStartOfDay(){
        if (fromDate == null) {
            return null;
        }
        Instant instant = fromDate.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime getEndOfDay(){
        if (toDate == null) {
            return null;
        }
        Instant instant = toDate.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

}

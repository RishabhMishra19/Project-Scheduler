package com.rishabh.scheduler.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Getter
public class GetScheduledAppointmentsRequestVo {

    @NotNull(message = "'Operator Id' is required")
    private Long operatorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "'Date' is required")
    private Date date;

    public LocalDateTime getStartOfDay(){
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDateTime getEndOfDay(){
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

}

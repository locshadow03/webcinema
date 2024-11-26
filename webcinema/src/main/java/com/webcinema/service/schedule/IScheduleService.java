package com.webcinema.service.schedule;

import com.webcinema.model.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IScheduleService {
    Schedule createSchedule(LocalDate startDate, LocalTime startTime, double price, Long movieId, Long branchId, Long roomId);
}

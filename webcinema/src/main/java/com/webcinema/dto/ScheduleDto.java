package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private int id;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    private Long movieId;
    private Long branchId;
    private Long roomId;

    private String status;
}

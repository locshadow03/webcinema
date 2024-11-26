package com.webcinema.controller;

import com.webcinema.dto.ScheduleDto;
import com.webcinema.model.Schedule;
import com.webcinema.service.schedule.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final IScheduleService scheduleService;

    @PostMapping("/create_schedule")
    public ResponseEntity<ScheduleDto> createSchedule(@RequestParam("startDate")LocalDate startDate,
                                                      @RequestParam("startTime")LocalTime startTime,
                                                      @RequestParam("price") double price,
                                                      @RequestParam("movieId") Long movieId,
                                                      @RequestParam("branchId") Long branchId,
                                                      @RequestParam("roomId") Long roomId
                                                      ){
        Schedule schedule = scheduleService.createSchedule(startDate, startTime, price, movieId, branchId, roomId);

        ScheduleDto scheduleDto = new ScheduleDto();

        if(schedule != null){
            scheduleDto.setStatus("Thêm thành công!");
        } else{
            scheduleDto.setStatus("Thêm thất bại!");
        }

        return ResponseEntity.ok(scheduleDto);
    }
}

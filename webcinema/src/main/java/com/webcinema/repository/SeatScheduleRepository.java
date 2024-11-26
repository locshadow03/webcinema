package com.webcinema.repository;

import com.webcinema.model.SeatSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatScheduleRepository extends JpaRepository<SeatSchedule, Long> {
    @Query("select sc from SeatSchedule sc where sc.seat.id = :seat_id and sc.schedule.id = :schedule_id")
    SeatSchedule findSeatScheduleById(@Param("seat_id") Long seat_id, @Param("schedule_id") Long schedule_id);
}

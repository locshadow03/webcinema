package com.webcinema.repository;

import com.webcinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Seat s WHERE s.room.id = :roomId")
    List<Seat> findAllSeatsByRoomId(@Param("roomId") Long roomId);
}

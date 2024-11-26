package com.webcinema.repository;

import com.webcinema.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select  tk from Ticket tk where tk.seatSchedule.id = :seatSchedule_id")
    Ticket findTicketBySeatScheduleId(@Param("seatSchedule_id") Long seatSchedule_id);
}

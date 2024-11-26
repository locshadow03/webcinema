package com.webcinema.service.ticket;

import com.webcinema.model.Ticket;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface ITicketService {

    Ticket createTicket(MultipartFile qrTicket, Long seatId, Long scheduleId) throws IOException, SQLException;

    void deleteTicketByIdSeatAndSchedule(Long seatId, Long scheduleId);

    Ticket getTicket(Long id);
}

package com.webcinema.controller;

import com.webcinema.dto.TicketDto;
import com.webcinema.model.Ticket;
import com.webcinema.repository.TicketRepository;
import com.webcinema.service.ticket.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final ITicketService ticketService;
    private final TicketRepository ticketRepository;

    @PostMapping("/add_ticket/{seat_id}/{schedule_id}")
    public ResponseEntity<TicketDto> addTicket(@RequestParam("imgqr")MultipartFile imgqr,
                                               @PathVariable Long seat_id,
                                               @PathVariable Long schedule_id
                                               ) throws SQLException, IOException {
        Ticket ticket = ticketService.createTicket(imgqr, seat_id, schedule_id);

        TicketDto ticketDto = new TicketDto();

        if(ticket != null){
            ticketDto.setTicket_id(ticket.getId());
            ticketDto.setTicket_status("Thêm thành công!");
            ticketDto.setName_seat(ticket.getSeatSchedule().getSeat().getNameSeat());
            ticketDto.setName_branch(ticket.getSeatSchedule().getSeat().getRoom().getBranch().getName());
            ticketDto.setName_room(ticket.getSeatSchedule().getSeat().getRoom().getNameRoom());
            byte[] photoBytes = imgqr.getBytes();

            String theimgqr = Base64.getEncoder().encodeToString(photoBytes);
            ticketDto.setImg_qr(theimgqr);
        }
        return ResponseEntity.ok(ticketDto);

    }

    @DeleteMapping("/{delete_id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long delete_id){
        Optional<Ticket> ticket = ticketRepository.findById(delete_id);

        if(ticket.isEmpty()){
            return new ResponseEntity<>(HttpStatus.valueOf("Không tìm thấy vé!"));
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{ticket_id}")
    public ResponseEntity<TicketDto> getTicketDetail(@PathVariable Long ticket_id){
        Ticket ticket = ticketService.getTicket(ticket_id);
        TicketDto ticketDto = new TicketDto();
        if(ticket != null){
            ticketDto.setTicket_id(ticket.getId());
            ticketDto.setName_branch(ticket.getSeatSchedule().getSeat().getRoom().getBranch().getName());
            ticketDto.setName_room(ticket.getSeatSchedule().getSeat().getRoom().getNameRoom());
            ticketDto.setName_seat(ticket.getSeatSchedule().getSeat().getNameSeat());


        }
    }


}

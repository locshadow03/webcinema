package com.webcinema.service.ticket;

import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.extension.SeatAlreadyBookedException;
import com.webcinema.model.Seat;
import com.webcinema.model.SeatSchedule;
import com.webcinema.model.Ticket;
import com.webcinema.repository.ScheduleRepository;
import com.webcinema.repository.SeatRepository;
import com.webcinema.repository.SeatScheduleRepository;
import com.webcinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService{
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatScheduleRepository seatScheduleRepository;
    @Override
    public Ticket createTicket(MultipartFile qrTicket, Long seatId, Long scheduleId) throws IOException, SQLException {
        Ticket ticket = new Ticket();

        byte[] photobytes = qrTicket.getBytes();
        Blob imgQr = new SerialBlob(photobytes);

        ticket.setQrImageURL(imgQr);

        SeatSchedule sc = seatScheduleRepository.findSeatScheduleById(seatId, scheduleId);

        if(sc != null){
            if(sc.getIsAvailable().equals(true)){
                sc.setIsAvailable(false);

                seatScheduleRepository.save(sc);

                ticket.setSeatSchedule(sc);
            } else{
                throw new SeatAlreadyBookedException("Ghế đã được đặt rồi!");
            }
        } else {
            throw new ResourceNotFoundException("Không tìm thấy ghế đã đang đặt!");
        }

        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketByIdSeatAndSchedule(Long seatId, Long scheduleId) {
        SeatSchedule sc = seatScheduleRepository.findSeatScheduleById(seatId, scheduleId);

        if(sc != null){
            sc.setIsAvailable(true);
            seatScheduleRepository.save(sc);

            Ticket ticket = ticketRepository.findTicketBySeatScheduleId(sc.getId());

            if(ticket != null){
                ticketRepository.deleteById(ticket.getId());
            } else{
                throw new ResourceNotFoundException("Không tìm thấy vé ghế!");
            }

        } else{
            throw new ResourceNotFoundException("Không tìm thấy ghế đã đang đặt!");
        }
    }

    @Override
    public Ticket getTicket(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        
        if(ticket.isPresent()){

            return ticket.get();
        } else{
            throw new ResourceNotFoundException("Không tìm thấy vé xem!");
        }
    }




}

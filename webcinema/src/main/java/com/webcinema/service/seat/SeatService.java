package com.webcinema.service.seat;

import com.webcinema.model.Seat;
import com.webcinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService{
    private final SeatRepository seatRepository;
    @Override
    public List<Seat> allSeatInRoom(Long roomId) {
        List<Seat> seats = seatRepository.findAllSeatsByRoomId(roomId);
        return seats;
    }
}

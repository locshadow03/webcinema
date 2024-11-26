package com.webcinema.controller;

import com.webcinema.dto.SeatDto;
import com.webcinema.model.Seat;
import com.webcinema.service.seat.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final ISeatService seatService;

    @GetMapping("/allSeat/{room_id}")
    public ResponseEntity<List<SeatDto>> allSeat(@PathVariable Long room_id){
        List<Seat> seats = seatService.allSeatInRoom(room_id);
        List<SeatDto> seatDtos = new ArrayList<>();

        for(Seat seat : seats){
            SeatDto seatDto = new SeatDto();

            seatDto.setSeat_id(seat.getId());
            seatDto.setNameSeat(seat.getNameSeat());
            seatDto.setIsAvailable(seat.getIsAvailable());

            seatDtos.add(seatDto);
        }

        return ResponseEntity.ok(seatDtos);
    }
}

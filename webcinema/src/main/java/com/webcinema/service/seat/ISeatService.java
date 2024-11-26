package com.webcinema.service.seat;

import com.webcinema.model.Seat;

import java.util.List;

public interface ISeatService {
    List<Seat> allSeatInRoom(Long roomId);
}

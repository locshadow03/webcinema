package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private Long seat_id;
    private String nameSeat;
    private Boolean isAvailable;

}

package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String nameRoom;
    private String imgRoom;
    private int numberOfRows;
    private int numberOfColumns;
    private String status;

}

package com.webcinema.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private Long ticket_id;
    private String img_qr;
    private String name_branch;
    private String name_movie;
    private String name_room;
    private String name_seat;
    private String ticket_status;
}

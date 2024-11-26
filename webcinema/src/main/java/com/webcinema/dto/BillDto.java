package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private Long bill_id;

    private String bill_code;

    private Long movie_id;

    private Long ticket_id;

    private LocalDate bookDate;

    private String paymentMethod;

    private String paymentStatus;

    private int totalAmount;

    private String name_movie;

    private String schedule;

    private List<TicketDto> ticketDtos;


}

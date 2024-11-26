package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorDto {
    private Long idActor;

    private String fullName;

    private String detail;

    private String imgActor;

    private String status;
}

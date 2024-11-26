package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto {
    private Long idDirector;

    private String fullName;

    private String detail;

    private String imgDirector;

    private String status;
}

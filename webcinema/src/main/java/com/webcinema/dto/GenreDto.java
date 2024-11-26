package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private Long genre_id;

    private String nameGenre;

    private String imgGenre;

    private String status;

}

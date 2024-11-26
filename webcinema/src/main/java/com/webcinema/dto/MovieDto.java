package com.webcinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String name;
    private String detail;
    private String imageURL;
    private int duration;
    private String trailerURL;
    private int isShowing;

    private String directorName;
    private List<String> actorNames;
    private List<Long> actorId;
    private List<String> genreNames;
    private List<Long> genreId;

    private String status;
}

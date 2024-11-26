package com.webcinema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "movie")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;
    private String name;
    private String detail;

    @Lob
    private Blob ImageURL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "director_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Director director;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MovieActors> movieActors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MovieGenres> movieGenres;

    @Column(nullable = false, updatable = false)
    private LocalDate releaseDate;
    private int duration;
    private String trailerURL;
    private int isShowing;

    @PrePersist
    protected void onCreate() {
        releaseDate = LocalDate.now();
    }

}
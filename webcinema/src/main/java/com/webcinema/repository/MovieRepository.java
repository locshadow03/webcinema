package com.webcinema.repository;

import com.webcinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m from Movie m where m.isShowing = 0")
    List<Movie> findAllByMovieClose();

    @Query("select m from Movie m where m.isShowing = 1")
    List<Movie> findAllByMovieOpen();
}

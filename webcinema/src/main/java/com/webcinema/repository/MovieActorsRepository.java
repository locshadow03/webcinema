package com.webcinema.repository;


import com.webcinema.model.MovieActors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieActorsRepository extends JpaRepository<MovieActors, Long> {

    @Query("select ma from MovieActors ma where ma.actor.id = :actorId and ma.movie.id = :movieId")
    Optional<MovieActors> findMovieActorsByActorId(@Param("actorId") Long actorId, @Param("movieId") Long movieId);

    @Query("select ma from MovieActors ma where ma.movie.id = :movieId")
    List<MovieActors> findMovieActorsByMovieId(@Param("movieId") Long movieId);

    @Modifying
    @Query("DELETE FROM MovieActors ma WHERE ma.movie.id = :movieId")
    void deleteByMovieId(@Param("movieId") Long movieId);

}

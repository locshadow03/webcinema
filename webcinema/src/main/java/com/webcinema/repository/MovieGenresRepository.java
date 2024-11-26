package com.webcinema.repository;

import com.webcinema.model.MovieGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieGenresRepository extends JpaRepository<MovieGenres, Long> {

    @Query("select mg from MovieGenres mg where mg.genre.id = :genreId and mg.movie.id = :movieId")
    Optional<MovieGenres> findMovieGenresByGenreId(@Param("genreId") Long genreId, @Param("movieId") Long movieId);

    @Query("select mg from MovieGenres mg where mg.movie.id = :movieId")
    List<MovieGenres> findMovieGenresByMovieId(@Param("movieId") Long movieId);

    @Modifying
    @Query("delete from MovieGenres mg where mg.movie.id = :movieId")
    void deleteByMovieId(@Param("movieId") Long movieId);
}

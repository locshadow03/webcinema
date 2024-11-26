package com.webcinema.repository;

import com.webcinema.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g.nameGenre FROM Genre g")
    List<String> findNameGenre();

    @Query("select g from Genre g where g.id = :genreId")
    Genre findByIdGenre(@Param("genreId") Long genreId);
}

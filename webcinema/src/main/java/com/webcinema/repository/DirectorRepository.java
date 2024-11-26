package com.webcinema.repository;

import com.webcinema.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query("select d.fullName FROM Director d")
    List<String> findNameDirector();

    @Query("select d from Director d where d.id = :directorId")
    Director findByIdDirector(@Param("directorId") Long directorId);
}

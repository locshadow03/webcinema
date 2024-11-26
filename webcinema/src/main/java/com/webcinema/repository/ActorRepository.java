package com.webcinema.repository;

import com.webcinema.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("select a.fullName from Actor a")
    List<String> findNameActor();

    @Query("select a from Actor a WHERE a.id = :actorId")
    Actor findByIdActor(@Param("actorId") Long actorId);
}

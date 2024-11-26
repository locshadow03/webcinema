package com.webcinema.service.actor;

import com.webcinema.model.Actor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IActorService{
    Actor addActor(String fullName, String detail, MultipartFile imgActor) throws IOException, SQLException;

    Actor updateActor(Long actor_id, String fullName, String detail, byte[] photoBytes);

    void deleteActor(Long actor_id);

    List<String> allNameActor();

    List<Actor> allActor();

    byte[] getActorPhotoById(Long actor_id) throws SQLException;

}

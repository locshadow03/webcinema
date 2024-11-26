package com.webcinema.service.actor;

import com.webcinema.extension.InternalServerException;
import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Actor;
import com.webcinema.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorServcice implements IActorService{
    private final ActorRepository actorRepository;
    @Override
    public Actor addActor(String fullName, String detail, MultipartFile imgActor) throws IOException, SQLException {
        Actor actor = new Actor();
        actor.setFullName(fullName);
        actor.setDetail(detail);
        if(!imgActor.isEmpty()){
            byte[] photoBytes = imgActor.getBytes();
            Blob photoImg = new SerialBlob(photoBytes);
            actor.setImgActor(photoImg);
        }
        return actorRepository.save(actor);
    }

    @Override
    public Actor updateActor(Long actor_id, String fullName, String detail, byte[] photoBytes) {
        Actor actor = actorRepository.findById(actor_id).orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy actor!"));

        if(fullName != null) actor.setFullName(fullName);
        if(detail != null) actor.setDetail(detail);

        if(photoBytes != null && photoBytes.length > 0){
            try {
                actor.setImgActor(new SerialBlob(photoBytes));
            }catch (SQLException ex){
                new InternalServerException("Error updating actor");
            }
        }
        return actorRepository.save(actor);
    }

    @Override
    public void deleteActor(Long actor_id) {
        Optional<Actor> actor = actorRepository.findById(actor_id);

        if(actor.isPresent()){
            actorRepository.deleteById(actor_id);
        }
    }

    @Override
    public List<String> allNameActor() {
        return actorRepository.findNameActor();
    }

    @Override
    public List<Actor> allActor() {
        return actorRepository.findAll();
    }

    @Override
    public byte[] getActorPhotoById(Long actor_id) throws SQLException {
        Optional<Actor> actor = actorRepository.findById(actor_id);

        if(actor.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Actor not found!");
        }

        Blob imgActor = actor.get().getImgActor();
        if(imgActor != null){
            return imgActor.getBytes(1, (int)imgActor.length());
        }
        return null;
    }
}

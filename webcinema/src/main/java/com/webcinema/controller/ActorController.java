package com.webcinema.controller;

import com.webcinema.dto.ActorDto;
import com.webcinema.model.Actor;
import com.webcinema.service.actor.IActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/actor")
@RequiredArgsConstructor
public class ActorController {
    private final IActorService actorService;

    @PostMapping("/add_actor")
    public ResponseEntity<ActorDto> addActor(@RequestParam("fullName") String fullName,
                                             @RequestParam("detail") String detail,
                                             @RequestParam("imgActor")MultipartFile imgActor
                                             ) throws SQLException, IOException {
        ActorDto actorDto = new ActorDto();

        Actor actor = actorService.addActor(fullName, detail, imgActor);
        if(actor != null){
            actorDto.setStatus("Thêm actor thành công!");
        } else{
            actorDto.setStatus("Thêm actor thất bại!");
        }

        return ResponseEntity.ok(actorDto);
    }

    @PutMapping("/update_actor/{actor_id}")
    public ResponseEntity<ActorDto> updateActor(@PathVariable Long actor_id,
                                                @RequestParam("fullName") String fullName,
                                                @RequestParam("detail") String detail,
                                                @RequestParam("imgActor")MultipartFile imgActor
                                                ) throws IOException, SQLException {
        byte[] photoBytes = imgActor != null && !imgActor.isEmpty() ? imgActor.getBytes() : actorService.getActorPhotoById(actor_id);

        Actor actor = actorService.updateActor(actor_id, fullName, detail, photoBytes);

        ActorDto actorDto = new ActorDto();
        if(actor != null){
            actorDto.setStatus("Cập nhật thành công!");
        } else{
            actorDto.setStatus("Cập nhật thất bại!");
        }

        return ResponseEntity.ok(actorDto);
    }

    @DeleteMapping("/delete/{actor_id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long actor_id){
        actorService.deleteActor(actor_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all_name_actor")
    public ResponseEntity<List<String>> allNameActor(){
        List<String> allNameActor = actorService.allNameActor();

        return ResponseEntity.ok(allNameActor);
    }

    @GetMapping("/all_actor")
    public ResponseEntity<List<ActorDto>> allActor() throws SQLException {
        List<ActorDto> actorDtos = new ArrayList<>();
        List<Actor> actors = actorService.allActor();

        for(Actor actor : actors){
            ActorDto actorDto = new ActorDto();

            actorDto.setIdActor(actor.getId());
            actorDto.setFullName(actor.getFullName());
            actorDto.setDetail(actor.getDetail());
            Blob photoActor = actor.getImgActor();

            byte[] photoBytes = photoActor.getBytes(1, (int) photoActor.length());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Actor = Base64.getEncoder().encodeToString(photoBytes);
                actorDto.setImgActor(base64Actor);
            }

            actorDtos.add(actorDto);
        }

        return ResponseEntity.ok(actorDtos);
    }
}

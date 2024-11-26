package com.webcinema.controller;

import com.webcinema.dto.DirectorDto;
import com.webcinema.model.Director;
import com.webcinema.service.director.IDirectorService;
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
@RequestMapping("/director")
@RequiredArgsConstructor
public class DirectorController {
    private final IDirectorService directorService;

    @PostMapping("/add_director")
    public ResponseEntity<DirectorDto> addDirector(@RequestParam("fullName") String fullName,
                                                @RequestParam("detail") String detail,
                                                @RequestParam("imgDirector") MultipartFile imgDirector
    ) throws SQLException, IOException {
        DirectorDto directorDto = new DirectorDto();

        Director director = directorService.addDirector(fullName, detail, imgDirector);
        if(director != null){
            directorDto.setStatus("Thêm director thành công!");
        } else{
            directorDto.setStatus("Thêm director thất bại!");
        }

        return ResponseEntity.ok(directorDto);
    }

    @PutMapping("/update_director/{director_id}")
    public ResponseEntity<DirectorDto> updateDirector(@PathVariable Long director_id,
                                                @RequestParam("fullName") String fullName,
                                                @RequestParam("detail") String detail,
                                                @RequestParam("imgDirector")MultipartFile imgDirector
    ) throws IOException, SQLException {
        byte[] photoBytes = imgDirector != null && !imgDirector.isEmpty() ? imgDirector.getBytes() : directorService.getDirectorPhotoById(director_id);

        Director director = directorService.updateDirector(director_id, fullName, detail, photoBytes);

        DirectorDto directorDto = new DirectorDto();
        if(director != null){
            directorDto.setStatus("Cập nhật thành công!");
        } else{
            directorDto.setStatus("Cập nhật thất bại!");
        }

        return ResponseEntity.ok(directorDto);
    }

    @DeleteMapping("/delete/{director_id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long director_id){
        directorService.deleteDirector(director_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all_name_director")
    public ResponseEntity<List<String>> allNameDirector(){
        List<String> allNameDirector = directorService.allNameDirector();

        return ResponseEntity.ok(allNameDirector);
    }

    @GetMapping("/all_director")
    public ResponseEntity<List<DirectorDto>> allDirector() throws SQLException {
        List<DirectorDto> directorDtos = new ArrayList<>();
        List<Director> directors = directorService.allDirector();

        for(Director director : directors){
            DirectorDto directorDto = new DirectorDto();

            directorDto.setIdDirector(director.getId());
            directorDto.setFullName(director.getFullName());
            directorDto.setDetail(director.getDetail());
            Blob photoDirector = director.getImgDirector();

            byte[] photoBytes = photoDirector.getBytes(1, (int) photoDirector.length());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Director = Base64.getEncoder().encodeToString(photoBytes);
                directorDto.setImgDirector(base64Director);
            }

            directorDtos.add(directorDto);
        }

        return ResponseEntity.ok(directorDtos);
    }
}

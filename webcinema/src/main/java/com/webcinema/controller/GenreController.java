package com.webcinema.controller;

import com.webcinema.dto.GenreDto;
import com.webcinema.model.Genre;
import com.webcinema.repository.GenreRepository;
import com.webcinema.service.genre.IGenreService;
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
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    private final IGenreService genreService;
    private final GenreRepository genreRepository;

    @PostMapping("/add_genre")
    public ResponseEntity<GenreDto> addGenre(@RequestParam("nameGenre") String nameGenre,
                                             @RequestParam("photoGenre")MultipartFile photoGenre
                                             ) throws SQLException, IOException {
        Genre genre = genreService.addGenre(nameGenre, photoGenre);

        GenreDto genreDto = new GenreDto();
        if(genre != null) {
            genreDto.setStatus("Thêm thành công!");
        } else{
            genreDto.setStatus("Thêm thất bại!");
        }

        return ResponseEntity.ok(genreDto);
    }

    @PutMapping("/update/{genre_id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long genre_id,
                                                @RequestParam("nameGenre") String nameGenre,
                                                @RequestParam("photoGenre")MultipartFile photoGenre
                                                ) throws IOException, SQLException {
        byte[] photoBytes = photoGenre != null && !photoGenre.isEmpty() ? photoGenre.getBytes() : genreService.getGenrePhotoById(genre_id);

        Genre genre = genreService.updateGenre(genre_id, nameGenre, photoBytes);
        GenreDto genreDto = new GenreDto();
        if(genre != null) {
            genreDto.setStatus("Cập nhật thành công!");
        } else{
            genreDto.setStatus("Cập nhật thất bại!");
        }

        return ResponseEntity.ok(genreDto);

    }

    @DeleteMapping("/delete/{genre_id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long genre_id){
        genreService.deleteGenre(genre_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all_name_genre")
    public ResponseEntity<List<String>> getAllNameGenre(){
        List<String> allNameGenre = genreService.allNameGenre();

        return ResponseEntity.ok(allNameGenre);
    }

    @GetMapping("/all_genre")
    public ResponseEntity<List<GenreDto>> allGenre() throws SQLException {

        List<Genre> genres = genreService.allGenre();
        List<GenreDto> genreDtos = new ArrayList<>();

        for(Genre genre: genres){
            GenreDto genreDto = new GenreDto();

            genreDto.setGenre_id(genre.getId());
            genreDto.setNameGenre(genre.getNameGenre());

            if(genre.getNameGenre() != null){
                Blob photoBlob = genre.getImgGenre();
                byte[] photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());
                String photoGenre = Base64.getEncoder().encodeToString(photoBytes);

                genreDto.setImgGenre(photoGenre);
            }

            genreDtos.add(genreDto);
        }

        return ResponseEntity.ok(genreDtos);
    }
}

package com.webcinema.service.genre;

import com.webcinema.extension.InternalServerException;
import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Genre;
import com.webcinema.repository.GenreRepository;
import com.webcinema.service.actor.IActorService;
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
public class GenreService implements IGenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre addGenre(String nameGenre, MultipartFile imgGenre) throws IOException, SQLException {
        Genre genre = new Genre();

        genre.setNameGenre(nameGenre);
        if(!imgGenre.isEmpty()){
            byte[] photoBytes = imgGenre.getBytes();
            Blob imgPhoto = new SerialBlob(photoBytes);

            genre.setImgGenre(imgPhoto);
        }

        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long genreId, String nameGenre, byte[] photoBytes) {
        Genre theGenre = genreRepository.findById(genreId).orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy genre!"));

        if(nameGenre != null) theGenre.setNameGenre(nameGenre);

        if(photoBytes != null && photoBytes.length > 0){
            try {
                theGenre.setImgGenre(new SerialBlob(photoBytes));
            }catch (SQLException ex){
                new InternalServerException("Error updating genre");
            }
        }
        return genreRepository.save(theGenre);
    }

    @Override
    public void deleteGenre(Long genreId) {
        Optional<Genre> genre = genreRepository.findById(genreId);

        if(!genre.isEmpty()){
            genreRepository.deleteById(genreId);
        }
    }

    @Override
    public List<String> allNameGenre() {
        return genreRepository.findNameGenre();
    }

    @Override
    public List<Genre> allGenre() {
        return genreRepository.findAll();
    }

    @Override
    public byte[] getGenrePhotoById(Long genreId) throws SQLException {
        Optional<Genre> genre = genreRepository.findById(genreId);

        if(genre.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Genre not found!");
        }

        Blob photoGenre = genre.get().getImgGenre();

        if(photoGenre != null){
            return photoGenre.getBytes(1,(int)photoGenre.length());
        }
        return new byte[0];
    }
}

package com.webcinema.service.genre;

import com.webcinema.model.Genre;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IGenreService {
    Genre addGenre(String nameGenre, MultipartFile imgGenre) throws IOException, SQLException;

    Genre updateGenre(Long genreId, String nameGenre, byte[] photoBytes);

    void deleteGenre(Long genreId);

    List<String> allNameGenre();

    List<Genre> allGenre();

    byte[] getGenrePhotoById(Long genreId) throws SQLException;
}

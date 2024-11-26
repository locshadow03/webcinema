package com.webcinema.service.director;

import com.webcinema.model.Director;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDirectorService {
    Director addDirector(String fullName, String detail, MultipartFile imgDirector) throws IOException, SQLException;

    Director updateDirector(Long director_id, String fullName, String detail, byte[] photoBytes);

    void deleteDirector(Long director_id);

    List<String> allNameDirector();

    List<Director> allDirector();

    byte[] getDirectorPhotoById(Long director_id) throws SQLException;
}

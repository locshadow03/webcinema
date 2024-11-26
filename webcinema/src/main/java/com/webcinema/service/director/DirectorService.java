package com.webcinema.service.director;

import com.webcinema.extension.InternalServerException;
import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Director;
import com.webcinema.repository.DirectorRepository;
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
public class DirectorService implements IDirectorService{
    private final DirectorRepository directorRepository;
    @Override
    public Director addDirector(String fullName, String detail, MultipartFile imgDirector) throws IOException, SQLException {
        Director director = new Director();
        director.setFullName(fullName);
        director.setDetail(detail);
        if(!imgDirector.isEmpty()){
            byte[] photoBytes = imgDirector.getBytes();
            Blob photoImg = new SerialBlob(photoBytes);
            director.setImgDirector(photoImg);
        }
        return directorRepository.save(director);
    }

    @Override
    public Director updateDirector(Long director_id, String fullName, String detail, byte[] photoBytes) {
        Director director = directorRepository.findById(director_id).orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy director!"));

        if(fullName != null) director.setFullName(fullName);
        if(detail != null) director.setDetail(detail);

        if(photoBytes != null && photoBytes.length > 0){
            try {
                director.setImgDirector(new SerialBlob(photoBytes));
            }catch (SQLException ex){
                new InternalServerException("Error updating director");
            }
        }
        return directorRepository.save(director);
    }

    @Override
    public void deleteDirector(Long director_id) {
        Optional<Director> director = directorRepository.findById(director_id);

        if(director.isPresent()){
            directorRepository.deleteById(director_id);
        }
    }

    @Override
    public List<String> allNameDirector() {
        return directorRepository.findNameDirector();
    }

    @Override
    public List<Director> allDirector() {
        return directorRepository.findAll();
    }

    @Override
    public byte[] getDirectorPhotoById(Long director_id) throws SQLException {
        Optional<Director> director = directorRepository.findById(director_id);

        if(director.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Director not found!");
        }

        Blob imgDirector = director.get().getImgDirector();
        if(imgDirector != null){
            return imgDirector.getBytes(1, (int)imgDirector.length());
        }
        return null;
    }
}

package com.webcinema.service.room;

import com.webcinema.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface IRoomService {
    Room addRoom(String nameRoom, MultipartFile imgRoom, int numberOfRows, int numberOfColumns, Long idBranch) throws IOException, SQLException;

    void deleteRoom(Long room_id);
}

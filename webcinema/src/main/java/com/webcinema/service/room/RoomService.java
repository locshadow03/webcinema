package com.webcinema.service.room;

import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Branch;
import com.webcinema.model.Room;
import com.webcinema.model.Seat;
import com.webcinema.repository.BranchRepository;
import com.webcinema.repository.RoomRepository;
import com.webcinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService{

    private final BranchRepository branchRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    @Override
    public Room addRoom(String nameRoom, MultipartFile imgRoom, int numberOfRows, int numberOfColumns, Long idBranch) throws IOException, SQLException {
        Branch branch = branchRepository.findById(idBranch).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi nhánh!"));
        Room room = new Room();
        room.setNumberOfRows(numberOfRows);
        room.setNumberOfColumns(numberOfColumns);
        if(!imgRoom.isEmpty()){
            byte[] photoBytes = imgRoom.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setImgRoom(photoBlob);
        }

        room.setBranch(branch);

        Room theRoom = roomRepository.save(room);

        char rowLetter = 'A';

        for(int i = 0; i < numberOfRows; i++){
            for(int j = 1; j <= numberOfColumns; j++){
                String seatName = rowLetter + String.valueOf(j);

                Seat seat = new Seat();

                seat.setNameSeat(seatName);
                seat.setRoom(theRoom);

                seatRepository.save(seat);
            }
            rowLetter++;
        }
        return room;
    }

    @Override
    public void deleteRoom(Long room_id) {
        Optional<Room> room = roomRepository.findById(room_id);
        if(room.isPresent()){
            roomRepository.deleteById(room_id);
        }
    }
}

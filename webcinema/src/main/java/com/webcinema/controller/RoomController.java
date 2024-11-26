package com.webcinema.controller;

import com.webcinema.dto.RoomDto;
import com.webcinema.model.Room;
import com.webcinema.service.room.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    private final IRoomService roomService;

    @PostMapping("/addRoom/{idBranch}")
    public ResponseEntity<RoomDto> addRoom(@PathVariable Long idBranch,
                                            @RequestParam("nameRoom") String nameRoom,
                                            @RequestParam("imgRoom")MultipartFile imgRoom,
                                            @RequestParam("numberOfRows") int numberOfRows,
                                            @RequestParam("numberOfColumns") int numberOfColumns
                                            ) throws SQLException, IOException {
        RoomDto roomDto = new RoomDto();

        Room room = roomService.addRoom(nameRoom,imgRoom, numberOfRows, numberOfColumns, idBranch);
        if(room != null){
            roomDto.setStatus("Thêm phòng thành công!");
        } else{
            roomDto.setStatus("Thêm phòng thất bại!");
        }

        return ResponseEntity.ok(roomDto);
    }

    @DeleteMapping("/deleteRoom/{room_id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long room_id){
        roomService.deleteRoom(room_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

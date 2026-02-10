package com.Click2Serve.Room.Controller;

import com.Click2Serve.Room.DTO.RoomDTO;
import com.Click2Serve.Room.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public RoomDTO createRoom(@RequestBody RoomDTO dto) {
        return roomService.createRoom(dto);
    }

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }


    @GetMapping("/{id}")
    public RoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }


    @PutMapping("/{id}")
    public RoomDTO updateRoom(@PathVariable Long id, @RequestBody RoomDTO dto) {
        return roomService.updateRoom(id, dto);
    }
}

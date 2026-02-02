package com.Click2Serve.Controller;

import com.Click2Serve.Dto.RoomDTO;
import com.Click2Serve.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // CREATE ROOM
    @PostMapping
    public RoomDTO createRoom(@RequestBody RoomDTO dto) {
        return roomService.createRoom(dto);
    }

    // GET ALL ROOMS
    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    // GET ROOM BY ID
    @GetMapping("/{id}")
    public RoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    // UPDATE ROOM
    @PutMapping("/{id}")
    public RoomDTO updateRoom(@PathVariable Long id, @RequestBody RoomDTO dto) {
        return roomService.updateRoom(id, dto);
    }
}

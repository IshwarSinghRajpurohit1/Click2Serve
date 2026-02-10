package com.Click2Serve.Room.Service;

import com.Click2Serve.Exception.ResponseClass;
import com.Click2Serve.Room.DTO.RoomDTO;
import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.Room.Entity.Room;
import com.Click2Serve.Hotel.Repository.HotelRepository;
import com.Click2Serve.Room.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;


    public ResponseEntity<Map<String, Object>> createRoom(RoomDTO dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(dto.getRoomType())
                .hotel(hotel)
                .build();

        RoomDTO dtos = mapToDTO(roomRepository.save(room));

      return    ResponseClass.responseSuccess("room hasbeen created","room", dtos);
    }


    public List<RoomDTO> getAllRooms() {
         List<RoomDTO> dtos = roomRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return (List<RoomDTO>) ResponseClass.responseSuccess("get all room","Room",dtos);
    }


    public ResponseEntity<Map<String, Object>> getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
            RoomDTO room1   = mapToDTO(room);
               return ResponseClass.responseSuccess("GOT ROOM","Room", room1);  
                
    }


    public ResponseEntity<Map<String, Object>> updateRoom(Long id, RoomDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());

        if (!room.getHotel().getId().equals(dto.getHotelId())) {
            Hotel hotel = hotelRepository.findById(dto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
            room.setHotel(hotel);
        }

         com.Click2Serve.Room.DTO.RoomDTO roomDTO  = mapToDTO(roomRepository.save(room));
         return ResponseClass.responseSuccess("room updated","room",roomDTO);
                  
    }


    private RoomDTO mapToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setHotelId(room.getHotel().getId());
        return dto;
    }
}

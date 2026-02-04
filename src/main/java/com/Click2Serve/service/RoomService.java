package com.Click2Serve.service;

import com.Click2Serve.Dto.RoomDTO;
import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.Room;
import com.Click2Serve.Repository.HotelRepository;
import com.Click2Serve.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;


    public RoomDTO createRoom(RoomDTO dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(dto.getRoomType())
                .hotel(hotel)
                .build();

        return mapToDTO(roomRepository.save(room));
    }


    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return mapToDTO(room);
    }


    public RoomDTO updateRoom(Long id, RoomDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());

        if (!room.getHotel().getId().equals(dto.getHotelId())) {
            Hotel hotel = hotelRepository.findById(dto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
            room.setHotel(hotel);
        }

        return mapToDTO(roomRepository.save(room));
    }


    private RoomDTO mapToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setHotelId(room.getHotel().getId());
        return dto;
    }
}

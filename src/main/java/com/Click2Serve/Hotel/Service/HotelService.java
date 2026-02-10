package com.Click2Serve.Hotel.Service;

import com.Click2Serve.Hotel.DTO.HotelDTO;
import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.Hotel.Repository.HotelRepository;
import com.Click2Serve.Status.HotelStatus;
import com.Click2Serve.QRGenerator.Service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final QrService qrService;


    @Transactional
    public HotelDTO createHotel(HotelDTO dto) {

        Hotel hotel = Hotel.builder()
                .hotelName(dto.getHotelName())
                .ownerName(dto.getOwnerName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .status(HotelStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        Hotel savedHotel = hotelRepository.save(hotel);


        qrService.generateQrForHotel(savedHotel);

        return mapToDTO(savedHotel);
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return mapToDTO(hotel);
    }

    public HotelDTO updateHotel(Long id, HotelDTO dto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setHotelName(dto.getHotelName());
        hotel.setOwnerName(dto.getOwnerName());
        hotel.setAddress(dto.getAddress());
        hotel.setPhone(dto.getPhone());
        hotel.setEmail(dto.getEmail());
        hotel.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(hotelRepository.save(hotel));
    }

    public void changeHotelStatus(Long id, HotelStatus status) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setStatus(status);
        hotel.setUpdatedAt(LocalDateTime.now());
        hotelRepository.save(hotel);
    }

    private HotelDTO mapToDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setHotelName(hotel.getHotelName());
        dto.setOwnerName(hotel.getOwnerName());
        dto.setAddress(hotel.getAddress());
        dto.setPhone(hotel.getPhone());
        dto.setEmail(hotel.getEmail());
        dto.setStatus(hotel.getStatus().name());
        return dto;
    }
}

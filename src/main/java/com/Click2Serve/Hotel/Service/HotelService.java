package com.Click2Serve.Hotel.Service;

import com.Click2Serve.Exception.ResponseClass;
import com.Click2Serve.Hotel.DTO.HotelDTO;
import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.Hotel.Repository.HotelRepository;
import com.Click2Serve.Status.HotelStatus;
import com.Click2Serve.QRGenerator.Service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final QrService qrService;


    @Transactional
    public ResponseEntity<Map<String, Object>> createHotel(HotelDTO dto) {

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

                  com.Click2Serve.Hotel.DTO.HotelDTO dtos = mapToDTO(savedHotel);
          return ResponseClass.responseSuccess("hotel created "," Created",dtos);
    }

    public List<HotelDTO> getAllHotels() {
         java.util.List<HotelDTO> dtos = hotelRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
          return (List<HotelDTO>) ResponseClass.responseFailure("Hotelfetch successfully","hotel", dtos);
    }

    public ResponseEntity<Map<String, Object>> getHotelById(Long id)
    {
        try {
            Hotel hotel = hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
            if (hotel == null) {
                ResponseClass.responseFailure("hotel not found");
            }
            HotelDTO dtos = mapToDTO(hotel);
           return  ResponseClass.responseSuccess("hotel found","hotel",dtos);
        }
        catch (Exception e)
        {
             return ResponseClass.internalServer("hotel not found");
        }

      }

    public ResponseEntity<Map<String, Object>> updateHotel(Long id, HotelDTO dto) {
        try {


            Hotel hotel = hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));

            hotel.setHotelName(dto.getHotelName());
            hotel.setOwnerName(dto.getOwnerName());
            hotel.setAddress(dto.getAddress());
            hotel.setPhone(dto.getPhone());
            hotel.setEmail(dto.getEmail());
            hotel.setUpdatedAt(LocalDateTime.now());
             HotelDTO dtos = mapToDTO(hotelRepository.save(hotel));
             return ResponseClass.responseSuccess("hotel update successfully","hotel",dtos);

        } catch (Exception e) {
                  return  ResponseClass.responseFailure("hotel not found");
        }
    }
    public Hotel changeHotelStatus(Long id, HotelStatus status) {
        try {
            Hotel hotel = hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));

            hotel.setStatus(status);
            hotel.setUpdatedAt(LocalDateTime.now());
           return hotelRepository.save(hotel);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

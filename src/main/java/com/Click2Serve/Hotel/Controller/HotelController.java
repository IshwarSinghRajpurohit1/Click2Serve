package com.Click2Serve.Hotel.Controller;

import com.Click2Serve.Hotel.DTO.HotelDTO;
import com.Click2Serve.Status.HotelStatus;
import com.Click2Serve.Hotel.Service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;


    @PostMapping
    public ResponseEntity<Map<String, Object>> createHotel(@RequestBody HotelDTO dto) {
        return hotelService.createHotel(dto);
    }


    @GetMapping
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateHotel(
            @PathVariable Long id,
            @RequestBody HotelDTO dto) {
        return hotelService.updateHotel(id, dto);
    }


    @PatchMapping("/{id}/status")
    public String changeStatus(
            @PathVariable Long id,
            @RequestParam HotelStatus status) {
        hotelService.changeHotelStatus(id, status);
        return "Hotel status updated";
    }
}

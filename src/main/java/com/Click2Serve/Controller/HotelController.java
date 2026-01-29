package com.Click2Serve.Controller;

import com.Click2Serve.Dto.HotelDTO;
import com.Click2Serve.Status.HotelStatus;
import com.Click2Serve.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // CREATE HOTEL
    @PostMapping
    public HotelDTO createHotel(@RequestBody HotelDTO dto) {
        return hotelService.createHotel(dto);
    }

    // GET ALL HOTELS
    @GetMapping
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    // GET HOTEL BY ID
    @GetMapping("/{id}")
    public HotelDTO getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    // UPDATE HOTEL
    @PutMapping("/{id}")
    public HotelDTO updateHotel(
            @PathVariable Long id,
            @RequestBody HotelDTO dto) {
        return hotelService.updateHotel(id, dto);
    }

    // CHANGE STATUS
    @PatchMapping("/{id}/status")
    public String changeStatus(
            @PathVariable Long id,
            @RequestParam HotelStatus status) {
        hotelService.changeHotelStatus(id, status);
        return "Hotel status updated";
    }
}

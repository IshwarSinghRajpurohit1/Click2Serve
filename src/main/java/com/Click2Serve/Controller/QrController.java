package com.Click2Serve.Controller;

import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.QrMaster;
import com.Click2Serve.Repository.HotelRepository;
import com.Click2Serve.service.QrService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrController {

    private final QrService qrService;
    private final HotelRepository hotelRepository;

    public QrController(QrService qrService, HotelRepository hotelRepository) {
        this.qrService = qrService;
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/qr/save")
    public QrMaster saveQR(@RequestParam Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        return qrService.generateQrForHotel(hotel);
    }
}



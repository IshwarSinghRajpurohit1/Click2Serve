package com.Click2Serve.Controller;

import com.Click2Serve.Entity.QrMaster;
import com.Click2Serve.service.QrService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrController {

    private final QrService qrService;

    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    // Example: http://localhost:8080/qr/save?hotelId=1
    @GetMapping("/qr/save")
    public QrMaster saveQR(@RequestParam Long hotelId) {
        return qrService.generateQrForHotel(hotelId);
    }
}

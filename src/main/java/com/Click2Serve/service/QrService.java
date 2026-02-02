package com.Click2Serve.service;

import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.QrMaster;
import com.Click2Serve.Repository.QrMasterRepository;
import com.Click2Serve.ImageGenerator.QRImageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrService {

    private final QrMasterRepository qrRepo;

    public QrMaster generateQrForHotel(Hotel hotel) {

        // 1️⃣ QR me hotel-specific URL (future menu)
        String qrText = "http://localhost:8080/menu/" + hotel.getId();

        // 2️⃣ Generate QR image file
        String fileName = "qr_hotel_" + hotel.getId() + ".png";
        String imagePath = QRImageGenerator.generateQrImage(qrText, fileName);

        // 3️⃣ Save QR info in DB
        QrMaster qr = new QrMaster();
        qr.setHotel(hotel);
        qr.setStatus("ACTIVE");
        qr.setQrCode(qrText);  // ya UUID nahi, actual URL
        // qr.setQrImagePath(imagePath); // agar column hai to uncomment karo

        return qrRepo.save(qr);
    }
}

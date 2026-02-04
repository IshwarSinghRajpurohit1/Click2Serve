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


        String qrText = "http://localhost:8080/menu/" + hotel.getId();


        String fileName = "qr_hotel_" + hotel.getId() + ".png";
        String imagePath = QRImageGenerator.generateQrImage(qrText, fileName);


        QrMaster qr = new QrMaster();
        qr.setHotel(hotel);
        qr.setStatus("ACTIVE");
        qr.setQrCode(qrText);


        return qrRepo.save(qr);
    }
}

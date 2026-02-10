package com.Click2Serve.QRGenerator.Service;

import com.Click2Serve.Hotel.Entity.Hotel;
import com.Click2Serve.QRGenerator.Entity.QrMaster;
import com.Click2Serve.QRGenerator.Repository.QrMasterRepository;
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

package com.Click2Serve.service;
import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.QrMaster;
import com.Click2Serve.Repository.QrMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QrService {

    @Autowired
    private final QrMasterRepository qrRepo;

    public QrService(QrMasterRepository qrRepo) {
        this.qrRepo = qrRepo;
    }

    public QrMaster generateQrForHotel(Hotel hotel) {

        QrMaster qr = new QrMaster();
        qr.setHotel(hotel);
        qr.setStatus("ACTIVE");
        qr.setQrCode(UUID.randomUUID().toString());
        return qrRepo.save(qr);

    }
}

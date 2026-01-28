package com.Click2Serve.service;

<<<<<<< HEAD




import com.Click2Serve.Entity.Hotel;
import com.Click2Serve.Entity.QrMaster;
import com.Click2Serve.Entity.QrMasterRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
=======
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
>>>>>>> 15a524846767d7db4d6521626e5984d354144796

@Service
public class QrService {

<<<<<<< HEAD
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
=======
    // 🔹 Browser mein QR dikhane ke liye
    public byte[] generateQRCode(String text) throws Exception {

        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    // 🔹 System mein QR file save karne ke liye
    public void saveQRCodeToFile(String text) throws Exception {

        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, 500, 500);

        Path path = Paths.get("/Users/scriza/Projects/Click2Serve/img/QRCode.png");
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
>>>>>>> 15a524846767d7db4d6521626e5984d354144796
    }
}

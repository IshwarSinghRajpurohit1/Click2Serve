package com.Click2Serve.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QrService {

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
    }
}

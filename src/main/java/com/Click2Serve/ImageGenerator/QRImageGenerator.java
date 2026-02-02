package com.Click2Serve.ImageGenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRImageGenerator {

    public static String generateQrImage(String qrText, String fileName) {

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    qrText,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );


            String folderPath = "qr-images";
            Path directoryPath = Paths.get(folderPath);


            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path filePath = directoryPath.resolve(fileName);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

            return filePath.toAbsolutePath().toString();

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR image", e);
        }
    }
}

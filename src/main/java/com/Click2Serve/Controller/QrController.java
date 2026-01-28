package com.Click2Serve.Controller;
import com.Click2Serve.service.QrService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrController {

    private final QrService qrCodeService;

    public QrController(QrService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQR(@RequestParam String text) throws Exception {
        return qrCodeService.generateQRCode(text);
    }
    @GetMapping("/qr/save")
    public String saveQR(@RequestParam String text) throws Exception
    {
        qrCodeService.saveQRCodeToFile(text);
        return "QR Code saved successfully";
    }

}

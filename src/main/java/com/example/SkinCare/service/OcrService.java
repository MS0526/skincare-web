package com.example.SkinCare.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class OcrService {

    @Value("${tess4j.datapath}")
    private String tessDataPath;

    public String extractTextFromImage(MultipartFile file) throws IOException, TesseractException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new IOException("이미지를 읽을 수 없습니다. 올바른 이미지 형식인지 확인해주세요.");
        }

        // 🔧 이미지 전처리 (grayscale, contrast 등)
        BufferedImage preprocessedImage = preprocessImage(image);

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessDataPath);
        tesseract.setLanguage("kor+eng");

        return tesseract.doOCR(preprocessedImage);
    }

    private BufferedImage preprocessImage(BufferedImage image) {
        BufferedImage gray = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gray.getGraphics().drawImage(image, 0, 0, null);
        return gray;
    }
}

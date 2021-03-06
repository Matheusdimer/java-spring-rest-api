package com.betha.cursomc.services;

import com.betha.cursomc.services.exceptions.FileException;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class ImageService {
    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
        String ext = FileUtils.getExtension(Objects.requireNonNull(uploadedFile.getOriginalFilename()));
        if (!ext.equals("png") && !ext.equals("jpg")) {
            throw new FileException("Somente imagens png e jpg são permitidas");
        }

        try {
            BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
            if (ext.equals("png")) {
                img = pngToJpg(img);
            }

            return img;
        } catch (IOException e) {
            throw new FileException("Erro na leitura do arquivo");
        }
    }

    public BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public ByteArrayOutputStream getOutputStream(BufferedImage img, String extension) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, extension, os);
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
        return os;
    }

    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = Math.min(sourceImg.getHeight(), sourceImg.getWidth());

        return Scalr.crop(
                sourceImg,
                (sourceImg.getWidth() / 2) - (min / 2),
                (sourceImg.getHeight() / 2) - (min / 2),
                min,
                min
        );
    }

    public BufferedImage resize(BufferedImage sourceImg, Integer imageSize) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, imageSize);
    }
}

package com.wsp.Gallery.services;

import com.wsp.Gallery.models.Image;
import com.wsp.Gallery.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Emilia on 2017-12-06.
 */
@Service
public class ImageServiceImpl implements ImageService {


    @Autowired
    final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public void removeImageById(long id) {
//        imageRepository.delete(id);
        imageRepository.deleteById(id);
    }

    //    @Override
//    public Image findById(long id) {
//        return imageRepository.findOne(id);
//    }
    @Override
    public Optional<Image> findById(long id) {
        return imageRepository.findById(id);
    }


    @Override
    public Optional<Image> findByIdAltered(long id, String kind) {
        return this.findByIdAltered(id, kind, 0);
    }

    @Override
    public Optional<Image> findByIdAltered(long id, String kind, Integer value) {
        Image img = imageRepository.findById(id).get();
        BufferedImage bi = createImageFromBytes(img.getBytes());

        if (kind.equals("grey")) {
            makeGrey(bi);
        } else if (kind.equals("sepia")) {
            makeSepia(bi);
        } else if (kind.equals("brightness")) {
            makeBrighter(bi, value);
        } else if (kind.equals("contrast")) {
            makeContrast(bi, value);
        }


        img.setBytes(createBytesFromImage(bi));
        img.setType("image/jpeg");
        return Optional.ofNullable(img);
    }


    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image addImage(byte[] bytes, String type) {
        Image img = new Image(bytes, type);
        return imageRepository.save(img);
    }


    private void makeGrey(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int nr, ng, nb;
                nr = ng = nb = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
                Color newColor = new Color(nr, ng, nb);
                image.setRGB(j, i, newColor.getRGB());
            }
        }

    }

    private void makeSepia(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int nr, ng, nb;
                nr = (int) (red * 0.393 + green * 0.769 + blue * 0.189);
                ng = (int) (red * 0.349 + green * 0.686 + blue * 0.168);
                nb = (int) (red * 0.272 + green * 0.534 + blue * 0.131);
                if(nr>255) {nr=255;}
                if(ng>255) {ng=255;}
                if(nb>255) {nb=255;}
                Color newColor = new Color(nr, ng, nb);
                image.setRGB(j, i, newColor.getRGB());
            }
        }

    }

    private void makeBrighter(BufferedImage image, Integer value) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int nr, ng, nb;
                nr = red + value;
                ng = green + value;
                nb = blue + value;
                if(nr>255) {nr=255;} else if(nr<0) {nr=0;}
                if(ng>255) {ng=255;} else if(ng<0) {ng=0;}
                if(nb>255) {nb=255;} else if(nb<0) {nb=0;}
                Color newColor = new Color(nr, ng, nb);
                image.setRGB(j, i, newColor.getRGB());
            }
        }

    }

    private void makeContrast(BufferedImage image, Integer value) {
        int width = image.getWidth();
        int height = image.getHeight();

        double f = (259.0 * ((double)(value) + 255.0)) / (255.0 * (259.0 - (double)(value)));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int nr, ng, nb;
                nr = (int) (f * (red - 128) + 128);
                ng = (int) (f * (green - 128) + 128);
                nb = (int) (f * (blue - 128) + 128);
                if(nr>255) {nr=255;} else if(nr<0) {nr=0;}
                if(ng>255) {ng=255;} else if(ng<0) {ng=0;}
                if(nb>255) {nb=255;} else if(nb<0) {nb=0;}
                Color newColor = new Color(nr, ng, nb);
                image.setRGB(j, i, newColor.getRGB());
            }
        }

    }


    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] createBytesFromImage(BufferedImage img) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}

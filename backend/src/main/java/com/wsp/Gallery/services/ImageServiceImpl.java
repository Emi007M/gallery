package com.wsp.Gallery.services;

import com.wsp.Gallery.models.Image;
import com.wsp.Gallery.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image addImage(byte[] image) {
        Image img = new Image();
        img.setBytes(image);
        return imageRepository.save(img);
    }
}

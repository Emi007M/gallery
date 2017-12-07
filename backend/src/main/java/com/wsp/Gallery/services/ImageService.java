package com.wsp.Gallery.services;

import com.wsp.Gallery.models.Image;

import java.util.Optional;

/**
 * Created by Emilia on 2017-12-06.
 */

public interface ImageService {

    void removeImageById(long id);

    Optional<Image> findById(long id);

    Iterable<Image> findAll();

    Image addImage(byte[] image);
}

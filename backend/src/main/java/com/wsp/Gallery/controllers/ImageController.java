package com.wsp.Gallery.controllers;

import com.wsp.Gallery.models.Image;
import com.wsp.Gallery.models.ImageDao;
import com.wsp.Gallery.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Emilia on 2017-12-06.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping()
    Iterable<Image> getImages() {
        return this.imageService.findAll();
    }

    @GetMapping(value = "/{imageId}")
    Image getImage(@PathVariable("imageId") Long id) {
        return this.imageService.findById(id).get();
    }

    @GetMapping(value = "/{imageId}/{kind}")
    Image getImage(@PathVariable("imageId") Long id, @PathVariable String kind) {
        return this.imageService.findByIdAltered(id, kind).get();
    }

    @GetMapping(value = "/{imageId}/{kind}/{value}")
    Image getImage(@PathVariable("imageId") Long id, @PathVariable String kind, @PathVariable Integer value) {
        return this.imageService.findByIdAltered(id, kind, value).get();
    }

    @PostMapping()
    public long addImage(@RequestBody ImageDao image) {
        return this.imageService.addImage(image.getBytes(), image.getType()).getId();
    }

    @DeleteMapping(value = "/{imageId}")
    public void deleteImage(@PathVariable("imageId") Long id) {
        this.imageService.removeImageById(id);
    }
}

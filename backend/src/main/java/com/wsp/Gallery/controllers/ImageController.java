package com.wsp.Gallery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wsp.Gallery.services.ImageService;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @GetMapping("/test")
    String getTest() {
        return "hello";
    }


    @GetMapping()
    Iterable<byte[]> getImages() {
        return StreamSupport.stream(this.imageService.findAll().spliterator(), false).map(i -> i.getBytes()).collect(Collectors.toList());
    }

    @GetMapping(value = "/{imageId}")
    byte[] getImage(@PathVariable("imageId") Long id) {

        //return this.imageService.findById(id).getBytes();
        return this.imageService.findById(id).get().getBytes();
    }

    @PostMapping()
    public long addImage(@RequestBody byte[] image) {
        return this.imageService.addImage(image).getId();
    }

    @DeleteMapping(value = "/{imageId}")
    public void deleteImage(@PathVariable("imageId") Long id) {
        this.imageService.removeImageById(id);
    }
}

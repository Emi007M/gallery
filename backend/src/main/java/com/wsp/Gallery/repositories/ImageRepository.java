package com.wsp.Gallery.repositories;

import com.wsp.Gallery.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Emilia on 2017-12-06.
 */
@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

}

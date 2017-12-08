package com.wsp.Gallery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Emilia on 2017-12-06.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDao {

    private byte[] bytes;
    private String type;

}

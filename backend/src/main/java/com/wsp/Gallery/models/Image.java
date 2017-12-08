package com.wsp.Gallery.models;
import javax.persistence.*;

import lombok.Data;

/**
 * Created by Emilia on 2017-12-06.
 */
@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=16777216) //long blob
    byte[] bytes;

    String type;

    public Image(byte[] bytes, String type){
        this.bytes = bytes;
        this.type = type;
    }

    public Image(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

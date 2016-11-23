package cz.muni.fi.pa165.api.dto;

import cz.muni.fi.pa165.entity.Album;

import java.util.Base64;

/**
 * Created by olda on 20.11.2016.
 */
public class ArtDTO {

    private Long id;

    private String imageType;

    private String imageName;

    private String image;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package com.vm62.diary.frontend.client.common.components;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

/**
 * Created by Ира on 26.12.2016.
 */
public enum Images {
    MAN(RESOURCES.man().getSafeUri().asString(), "man"),
    WOMAN(RESOURCES.woman().getSafeUri().asString(), "woman"),
    USER_BG(RESOURCES.userBG().getSafeUri().asString(), "userBG");

    private String image;
    private String descriptionOfImage;

    Images(String pathToImage, String descriptionOfImage){
        this.image = pathToImage;
        this.descriptionOfImage = descriptionOfImage;
    }

    public String getImage() {
        return image;
    }

    public String getDescriptionOfImage(){
        return descriptionOfImage;
    }

}

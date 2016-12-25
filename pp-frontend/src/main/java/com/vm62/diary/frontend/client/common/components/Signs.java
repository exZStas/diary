package com.vm62.diary.frontend.client.common.components;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

public enum Signs {
    ALERT_SIGN(RESOURCES.alert().getSafeUri().asString(), "alertSign");

    private String image;
    private String descriptionOfImage;

    Signs(String pathToImage, String descriptionOfImage){
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

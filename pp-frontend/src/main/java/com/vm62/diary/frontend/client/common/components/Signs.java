package com.vm62.diary.frontend.client.common.components;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

public enum Signs {
    ALERT_SIGN(RESOURCES.alert().getSafeUri().asString(), "alertSign"),
    MAN(RESOURCES.man().getSafeUri().asString(), "man"),
    WOMAN(RESOURCES.woman().getSafeUri().asString(), "woman"),
    QUESTION_SIGN(RESOURCES.question().getSafeUri().asString(), "questionSign"),
    USER_BG(RESOURCES.userBG().getSafeUri().asString(), "userBG");

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

package com.vm62.diary.frontend.client.common.components;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

public enum Signs {
    ALERT_SIGN(RESOURCES.alert().getSafeUri().asString(), "alertSign"),
    BIRD_SIGN(RESOURCES.bird().getSafeUri().asString(), "birdSign"),
    EDUCATION_SIGN(RESOURCES.education().getSafeUri().asString(), "educationSign"),
    HOMEWORK_SIGN(RESOURCES.homework().getSafeUri().asString(), "homeworkSign"),
    SLEEP_SIGN(RESOURCES.sleep().getSafeUri().asString(), "sleepSign"),
    WORK_SIGN(RESOURCES.work().getSafeUri().asString(), "workSign"),
    EATING_SIGN(RESOURCES.eating().getSafeUri().asString(), "eatingSign"),
    QUESTION_SIGN(RESOURCES.question().getSafeUri().asString(), "questionSign"),
    HOUSEHOLDCHORES_SIGN(RESOURCES.householdchores().getSafeUri().asString(), "householdchoresSign"),
    SPORT_SIGN(RESOURCES.sport().getSafeUri().asString(), "sportSign"),
    TRIP_SIGN(RESOURCES.trip().getSafeUri().asString(), "tripSign"),
    ENTERTAIMENT_SIGN(RESOURCES.entertaiment().getSafeUri().asString(), "entertaimentSign");


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

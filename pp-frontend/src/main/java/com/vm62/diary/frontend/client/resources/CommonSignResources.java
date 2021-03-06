package com.vm62.diary.frontend.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface CommonSignResources extends ClientBundle {
    CommonSignResources RESOURCES = GWT.create(CommonSignResources.class);

    @Source("img/alert.png")
    ImageResource alert();

    @Source("img/question.png")
    ImageResource question();

    @Source("img/men.png")
    ImageResource man();

    @Source("img/woman.png")
    ImageResource woman();

    @Source("CssSignResources.css")
    CommonSignResourcesCss style();

    @Source("img/user_bg.jpg")
    ImageResource userBG();

    @Source("img/star.png")
    ImageResource star();

    @Source("img/sport.png")
    ImageResource sport();

    @Source("img/work.png")
    ImageResource work();

    @Source("img/entertaiment.png")
    ImageResource entertaiment();

    @Source("img/trip.png")
    ImageResource trip();

    @Source("img/household chores.png")
    ImageResource householdchores();

    @Source("img/homework.png")
    ImageResource homework();

    @Source("img/eating.png")
    ImageResource eating();

    @Source("img/education.png")
    ImageResource education();

    @Source("img/sleep.png")
    ImageResource sleep();

    @Source("img/bird.png")
    ImageResource bird();

    interface CommonSignResourcesCss extends CssResource{
        String alert();

        String alertSigns();

        String usualSignImageStyle();

        String chosenSignImageStyle();

        String question();

        String man();

        String woman();

        String userBG();
    }
}

package com.vm62.diary.frontend.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface CommonSignResources extends ClientBundle {
    CommonSignResources RESOURCES = GWT.create(CommonSignResources.class);

    @Source("img/alert.png")
    ImageResource alert();

    @Source("CssSignResources.css")
    CommonSignResourcesCss style();

    interface CommonSignResourcesCss extends CssResource{
        String alert();

        String alertSigns();
    }
}

package com.vm62.diary.frontend.client.activity;

import com.google.gwt.event.dom.client.ClickHandler;
import gwt.material.design.client.constants.IconType;

public interface ITopHeaderController {

    void setHeaderTitle(String title);

    void setIcon(IconType iconType);

    void setBreadcrumb(String label, ClickHandler clickHandler);

    void setBreadcrumbVisible(boolean visible);
}
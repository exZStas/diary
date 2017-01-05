package com.vm62.diary.frontend.client.common;

import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.Date;

public interface BaseActivity<T extends NavigationPlace> {

    public static Date today = new Date();
    public abstract void start(HasWidgets display, T place);
    public abstract void stop();

}
package com.vm62.diary.frontend.client.common.navigation;

import com.vm62.diary.frontend.client.activity.MainPanelActivity;

import com.vm62.diary.frontend.client.common.BaseActivity;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class NavigationManager {

    @Inject
    Provider<MainPanelActivity> mainPanelActivityProvider;
//    @Inject
//    Provider<ActivityAsyncProxy<UserActivity>> UserActivityProxy;

    private BaseActivity currentActivity = null;

    private HasWidgets currentActivityWidget = null;
    private HasWidgets bodyPanel;
    private HasWidgets contentPanel;

    public NavigationManager() {
        //default constructor
    }

    public void init(HasWidgets bodyPanel, HasWidgets contentPanel) {
        this.bodyPanel = bodyPanel;
        this.contentPanel = contentPanel;
    }

    private void stopCurrentActivity() {
        if (currentActivity != null) {
            currentActivity.stop();
            currentActivity = null;
        }
    }

    private void clearCurrentWidget() {
        if (currentActivityWidget != null) {
            currentActivityWidget.clear();
            currentActivityWidget = null;
        }
    }

    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void navigate(NavigationPlace place) {
        if (place == null || place.getUrl() == null) {
            return;
        }

        stopCurrentActivity();
        clearCurrentWidget();

        switch (place.getUrl()) {
            // body
            case URL_MAIN:
                currentActivity = mainPanelActivityProvider.get();
                currentActivityWidget = bodyPanel;
                break;
            // content
//            case URL_ECONSULT_OVERVIEW:
//                currentActivity = UserActivityProxy.get();
//                currentActivityWidget = contentPanel;
//                break;
        }
        currentActivity.start(currentActivityWidget, place);
    }
}

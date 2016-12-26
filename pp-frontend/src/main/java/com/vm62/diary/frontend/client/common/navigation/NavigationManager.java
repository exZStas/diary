package com.vm62.diary.frontend.client.common.navigation;

import com.vm62.diary.frontend.client.activity.MainPanelActivity;

import com.vm62.diary.frontend.client.activity.admin.AdminHomeActivity;
import com.vm62.diary.frontend.client.activity.diarylist.ChangeProfileActivity;
import com.vm62.diary.frontend.client.activity.diarylist.CreateEventActivity;
import com.vm62.diary.frontend.client.activity.diarylist.DiaryListActivity;
import com.vm62.diary.frontend.client.activity.diarylist.EditEventViewActivity;
import com.vm62.diary.frontend.client.activity.login.RegistrationActivity;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.injection.ActivityAsyncProxy;

@Singleton
public class NavigationManager {

    @Inject
    Provider<MainPanelActivity> mainPanelActivityProvider;
    @Inject
    Provider<ActivityAsyncProxy<ChangeProfileActivity>> changeProfileActivityProxy;
    @Inject
    Provider<ActivityAsyncProxy<RegistrationActivity>> registrationActivityProxy;
    @Inject
    Provider<ActivityAsyncProxy<AdminHomeActivity>> adminHomeActivityProxy;

    @Inject
    Provider<ActivityAsyncProxy<DiaryListActivity>> diaryActivityProxy;

    @Inject
    Provider<ActivityAsyncProxy<CreateEventActivity>> eventActivityProxy;
    @Inject
    Provider<ActivityAsyncProxy<EditEventViewActivity>> editActivityProxy;


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
            case URL_REGISTRATION_ACTIVITY:
                currentActivity = registrationActivityProxy.get();
                currentActivityWidget = contentPanel;
                break;
            case URL_DIARY_ACTIVITY:
                currentActivity = diaryActivityProxy.get();
                currentActivityWidget = contentPanel;
                break;
            case URL_CREATE_EVENT_ACTIVITY:
                currentActivity = eventActivityProxy.get();
                currentActivityWidget = contentPanel;
                break;
            case URL_CHANGE_PROFILE_ACTIVITY:
                currentActivity = changeProfileActivityProxy.get();
                currentActivityWidget = contentPanel;
                break;
            case URL_HOME_ADMIN:
                currentActivity = adminHomeActivityProxy.get();
                currentActivityWidget = contentPanel;
            case URL_EDIT_EVENT_ACTIVITY:
                currentActivity = editActivityProxy.get();
                currentActivityWidget = contentPanel;
                break;
        }
        currentActivity.start(currentActivityWidget, place);
    }
}

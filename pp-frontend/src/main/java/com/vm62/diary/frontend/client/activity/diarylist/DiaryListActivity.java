package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.constants.Gender;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.messages.CommonMessages;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.client.service.LoginServiceAsync;
import com.vm62.diary.frontend.client.service.UserProfileServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import com.vm62.diary.frontend.server.service.dto.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Ира on 17.11.2016.
 */
@Singleton
public class DiaryListActivity implements BaseActivity{
    @ImplementedBy(DiaryListView.class)
    public interface IDiaryListView extends IsWidget {
        void setUserName(String name);
        void setUserGroup(String group);
        void setUserPicture(Gender gender);
        void setSchedule(List<EventDTO> events);
        void setNewEvent(EventDTO event);
        void addChartButtonClickHandler (ClickHandler handler);
        void addEventsButtonClickHandler (ClickHandler handler);
        void setChartParameters();
        void setDiaryListParameters();
        void createPieCharts( Map<String,Long> unDone, Map<String,Long> done);
        void setDayOfList(Date today);
        void updateSchedule(ClickHandler handler);
        void buttonScrollRightClick(ClickHandler handler);
        void buttonScrollLeftClick(ClickHandler handler);
        void onClickLogOutBtn(ClickHandler handler);
        Date getToday();
        String getUserGroup();
    }

    private EventServiceAsync eventServiceAsync;
    private IDiaryListView view;
    private NotificationManager notificationManager;
    private UserProfileServiceAsync userProfileServiceAsync;
    private List<EventDTO> eventDTOs;
    private NavigationManager navigationManager;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);
    private CommonMessages messages = GWT.create(CommonMessages.class);
    private LoginServiceAsync loginServiceAsync;

    @Inject
    DiaryListActivity(IDiaryListView view, NotificationManager notificationManager, NavigationManager navigationManager, EventServiceAsync eventServiceAsync, UserProfileServiceAsync userProfileServiceAsync, LoginServiceAsync loginServiceAsync){

        this.view = view;
        this.eventServiceAsync = eventServiceAsync;
        this.notificationManager = notificationManager;
        this.userProfileServiceAsync = userProfileServiceAsync;
        this.navigationManager = navigationManager;
        this.loginServiceAsync = loginServiceAsync;
        setEventForDay();
        this.view.setDayOfList(view.getToday());
        addEventHandlers();



    }
    public void addEventHandlers() {
        userProfileServiceAsync.getUser(new AsyncCallback<UserDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails(constants.errorProfileIsNotAvailable());
            }

            @Override
            public void onSuccess(UserDTO result) {
                view.setUserName(result.getFirstName()+" "+result.getLastName());
                view.setUserPicture(Gender.valueOf(result.getGender()));
                view.setUserGroup(result.getStudyGroup());
            }
        });
        view.addChartButtonClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                e.preventDefault();
                e.stopPropagation();
                eventServiceAsync.getEventsByDayForUser(view.getToday(), new AsyncCallback<List<EventDTO>>(){
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails(constants.errorEventsAreNotAvailable());
                    }

                    @Override
                    public void onSuccess(List<EventDTO> result) {
                        eventDTOs = result;
                        if (eventDTOs.isEmpty()) notificationManager.showErrorPopupWithoutDetails(constants.errorCurrentDayHasNoEvents());
                        else navigationManager.navigate(new ChartViewActivity.ChartViewActivityPlace(eventDTOs));

                    }
                });

            }
        });
        view.addEventsButtonClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
                event.stopPropagation();
                view.setDiaryListParameters();
                //navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
            }
        });
        view.updateSchedule(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                eventServiceAsync.scheduleUpdate(view.getUserGroup(), view.getToday(), new AsyncCallback<Date>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails(constants.errorCanNotUpdateSchedule());
                    }

                    @Override
                    public void onSuccess(Date result) {
                        notificationManager.showInfoPopup(messages.updatedTo(result));
                        setEventForDay();
                    }
                });

            }
        });
        view.buttonScrollRightClick(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Date today = new Date(view.getToday().getTime() + (1000 * 60 * 60 * 24));
                view.setDayOfList(today);
                setEventForDay();
            }
        });
        view.buttonScrollLeftClick(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Date today = new Date(view.getToday().getTime() - (1000 * 60 * 60 * 24));
                view.setDayOfList(today);
                setEventForDay();
            }
        });
        view.onClickLogOutBtn(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                loginServiceAsync.logOut(new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails("Connection failed - please retry.");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        Window.Location.assign("/");
                    }
                });
            }
        });

    }
    private void setEventForDay(){
        eventServiceAsync.getEventsByDayForUser(view.getToday(), new AsyncCallback<List<EventDTO>>(){
            @Override
            public void onFailure(Throwable caught) {
                notificationManager.showErrorPopupWithoutDetails(constants.errorEventsAreNotAvailable());
            }

            @Override
            public void onSuccess(List<EventDTO> result) {
                view.setSchedule(result);
            }
        });
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }
}

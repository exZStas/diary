package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
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
        void setUserPicture(Gender gender);
        void setSchedule(List<EventDTO> events);
        void setNewEvent(EventDTO event);
        void addChartButtonClickHandler (ClickHandler handler);
        void setDiaryList();
        void setChartParameters(Map<String,Long> dicUndone, Map<String,Long> dicDone);
        void setDayOfList(Date today);

    }
    @ImplementedBy(EventView.class)
    public interface IEventView extends IsWidget {

        void setEventParameters(EventDTO event);

    }
    private EventServiceAsync eventServiceAsync;
    private IDiaryListView view;
    private Date today = new Date();
    private NotificationManager notificationManager;
    private UserProfileServiceAsync userProfileServiceAsync;
    private List<EventDTO> eventDTOs;
    private NavigationManager navigationManager;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    @Inject
    DiaryListActivity(IDiaryListView view, NotificationManager notificationManager, NavigationManager navigationManager, EventServiceAsync eventServiceAsync, UserProfileServiceAsync userProfileServiceAsync){

        this.view = view;
        this.eventServiceAsync = eventServiceAsync;
        this.notificationManager = notificationManager;
        this.userProfileServiceAsync = userProfileServiceAsync;
        this.navigationManager = navigationManager;
        this.view.setDiaryList();
        this.view.setDayOfList(today);
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
            }
        });
        view.addChartButtonClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                e.preventDefault();
                e.stopPropagation();
                eventServiceAsync.getEventsByDayForUser(today, new AsyncCallback<List<EventDTO>>(){
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
//        eventServiceAsync.getEventsByDayForUser(today, new AsyncCallback<List<EventDTO>>(){
//            @Override
//            public void onFailure(Throwable caught) {
//                notificationManager.showErrorPopupWithoutDetails("Events is not avalible!");
//            }
//
//            @Override
//            public void onSuccess(List<EventDTO> result) {
//                view.setSchedule(result);
//
//            }
//        });
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }
}

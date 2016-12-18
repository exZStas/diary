package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;

import java.util.Date;

/**
 * Created by Ира on 15.12.2016.
 */

@Singleton
public class CreateEventActivity implements BaseActivity {
    @ImplementedBy(CreateEventView.class)
    public interface ICreateEventView extends IsWidget {
        String getName();
        String getType();
        Boolean getComplexity();
        Date getStartTime();
        int getDuration();
        Date getEndTime();
        Date getEndDate();

        void registerPatientHandler(SimpleEventHandler handler);
    }

    private ICreateEventView view;
    private EventServiceAsync eventServiceAsync;
    private NotificationManager notificationManager;

    @Inject
    CreateEventActivity(ICreateEventView view, EventServiceAsync eventServiceAsync, NotificationManager notificationManager) {

        this.view = view;
//        this.eventServiceAsync = eventServiceAsync;
        this.notificationManager = notificationManager;
        addEventHandlers();
    }

    private void addEventHandlers() {
//        view.registerPatientHandler(new SimpleEventHandler() {
//            @Override
//            public void onEvent() {
//                eventServiceAsync.create(view.getName(), view.getType(),view.getStartTime(),view.getEndTime(),view.getDuration(),1, new AsyncCallback<EventDTO>() {
//                            @Override
//                            public void onFailure(Throwable caught) {
//                                notificationManager.showErrorPopupWithoutDetails("Event was canceled!");
//                            }
//
//                            @Override
//                            public void onSuccess(EventDTO result) {
//                                notificationManager.showErrorPopupWithoutDetails("Event create!");
//                            }
//                        });
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

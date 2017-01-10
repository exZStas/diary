package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.components.SignImageListWidget;
import com.vm62.diary.frontend.client.common.components.Signs;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.events.SelectEventHandler;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.service.EventService;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import gwt.material.design.client.ui.MaterialRow;
import java.util.Date;
import java.util.Map;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

/**
 * Created by Ира on 15.12.2016.
 */

//@Singleton
public class CreateEventActivity implements BaseActivity {
    @ImplementedBy(CreateEventView.class)
    public interface ICreateEventView extends IsWidget {
        String getName();
        String getDescription();
        Category getCategory();
        Boolean getComplexity();
        void setToday(Date today);
        Date getStartTime();
        Long getDuration();
        Date getEndTime();
        void setSignImageList(SignImageListWidget signImageListWidget);
        void registerPatientHandler(SimpleEventHandler handler);
        void changeForm(String name, String description, Category category, String sticker, Boolean complexity, Date startTime,
                        Date endTime, Long duration);
    }


    private ICreateEventView view;
    private EventServiceAsync eventServiceAsync;
    private NotificationManager notificationManager;
    private SignImageListWidget signImageListWidget;
    private DiaryListActivity.IDiaryListView diaryListView;
    private String eventStickerDescription;
    private NavigationManager navigationManager;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    @Inject
    CreateEventActivity(ICreateEventView view, EventServiceAsync eventServiceAsync, NotificationManager notificationManager,
                        SignImageListWidget signImageListWidget, NavigationManager navigationManager, DiaryListActivity.IDiaryListView diaryListView) {

        this.view = view;
        this.diaryListView = diaryListView;
        this.navigationManager = navigationManager;
        this.signImageListWidget = signImageListWidget;
        this.eventServiceAsync = eventServiceAsync;
        this.notificationManager = notificationManager;
        this.view.setSignImageList(signImageListWidget);
        this.view.setToday(diaryListView.getToday());
        addEventHandlers();
    }

    private void addEventHandlers() {

        signImageListWidget.addSelectHandler(new SelectEventHandler<Signs>() {
            @Override
            public void onEvent(Signs selectedObject) {
                String chosenImageStyle = RESOURCES.style().chosenSignImageStyle();
                String usualImageStyle = RESOURCES.style().usualSignImageStyle();
                eventStickerDescription = selectedObject.getDescriptionOfImage();
                MaterialRow signPanel = signImageListWidget.getSignPanel();
                int widgetsCount = signPanel.getWidgetCount();
                for(int indexOfImage = 0; indexOfImage < widgetsCount; indexOfImage++){
                    Image currentImage = (Image) signPanel.getWidget(indexOfImage);
                    currentImage.setStyleName(usualImageStyle);
                }
                for(int indexOfImage = 0; indexOfImage < widgetsCount; indexOfImage++){
                    for(Signs sign : Signs.values()){
                        Image currentImage = (Image) signPanel.getWidget(indexOfImage);
                        boolean isCurrentImage = currentImage.getUrl().equals(sign.getImage()) && selectedObject.getDescriptionOfImage().equals(sign.getDescriptionOfImage());
                        if(isCurrentImage){
                            currentImage.setStyleName(chosenImageStyle);
                            eventStickerDescription = selectedObject.getDescriptionOfImage();
                        }
                    }
                }
            }
        });

        view.registerPatientHandler(new SimpleEventHandler() {
            @Override
            public void onEvent() {
                if (view.getComplexity()){
                    Date startDateTime = new Date(view.getStartTime().getTime());
                    Date endDateTime = new Date(view.getEndTime().getTime());

                    Integer days = (int)(trimDate(endDateTime).getTime() - trimDate(startDateTime).getTime())/ (1000 * 60 * 60 * 24);
                    if (days>0) {
                        if (view.getDuration() / (60000 * days) < 60) {
                            notificationManager.showErrorPopupWithoutDetails(constants.errorSmallEventTime());
                        } else
                            eventServiceAsync.createComplexEvent(view.getName(), view.getDescription(), view.getCategory(), view.getStartTime(), view.getEndTime(),
                                view.getComplexity(), view.getDuration(), eventStickerDescription, days, new AsyncCallback<EventDTO>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        notificationManager.showErrorPopupWithoutDetails(constants.errorEventWasCanceled());
                                    }

                                    @Override
                                    public void onSuccess(EventDTO result) {
                                        notificationManager.showInfoPopup(constants.successEventWasCreated());
                                        diaryListView.setNewEvent(result);
                                    }
                                });
                    }
                }
                else
                eventServiceAsync.create(view.getName(), view.getDescription() ,view.getCategory(), view.getStartTime(),view.getEndTime(),view.getComplexity(),view.getDuration(), eventStickerDescription, new AsyncCallback<EventDTO>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                notificationManager.showErrorPopupWithoutDetails(constants.errorEventWasCanceled());
                            }

                            @Override
                            public void onSuccess(EventDTO result) {
                                notificationManager.showInfoPopup(constants.successEventWasCreated());
                                diaryListView.setNewEvent(result);
//                                diaryListView.asWidget().setVisible(false);
//                                diaryListView.asWidget().setVisible(true);
//                                diaryListView.setNewEvent(result);
//                                navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_DIARY_ACTIVITY));
                                // // TODO: 26.12.2016 implement
                            }
                        });
            }
        });
    }
    private Date trimDate(Date date){
        Date day = new Date(0,0,0,0,0,0);
        day.setYear(date.getYear());
        day.setMonth(date.getMonth());
        day.setDate(date.getDate());
        return day;
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        display.add((Widget) view);
    }

    @Override
    public void stop() {

    }

}

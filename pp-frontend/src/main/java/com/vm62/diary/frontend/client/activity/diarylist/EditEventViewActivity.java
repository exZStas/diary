package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.vm62.diary.frontend.client.common.BaseActivity;
import com.vm62.diary.frontend.client.common.components.SignImageListWidget;
import com.vm62.diary.frontend.client.common.components.Signs;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.events.SelectEventHandler;
import com.vm62.diary.frontend.client.common.events.SimpleEventHandler;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import gwt.material.design.client.ui.MaterialRow;

import static com.vm62.diary.frontend.client.resources.CommonSignResources.RESOURCES;

/**
 * Created by Ира on 26.12.2016.
 */
public class EditEventViewActivity implements BaseActivity {
    
    private CreateEventActivity.ICreateEventView view;
    private EventServiceAsync eventServiceAsync;
    private NotificationManager notificationManager;
    private SignImageListWidget signImageListWidget;
    private DiaryListActivity.IDiaryListView diaryListView;
    private String eventStickerDescription;
    private NavigationManager navigationManager;
    private EditEventActivityPlace place;
    private DiaryConstants constants = GWT.create(DiaryConstants.class);
    @Inject
    EditEventViewActivity(CreateEventActivity.ICreateEventView view , EventServiceAsync eventServiceAsync, NotificationManager notificationManager,
                          SignImageListWidget signImageListWidget, NavigationManager navigationManager, DiaryListActivity.IDiaryListView diaryListView)
    {
        this.view = view;this.view = view;
        this.diaryListView = diaryListView;
        this.navigationManager = navigationManager;
        this.signImageListWidget = signImageListWidget;
        this.eventServiceAsync = eventServiceAsync;
        this.notificationManager = notificationManager;
        this.view.setSignImageList(signImageListWidget);
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
                eventServiceAsync.update(place.eventDTO.getId(),view.getName(), view.getDescription() ,view.getCategory(), view.getStartTime(),
                        view.getEndTime(),view.getComplexity(),view.getDuration(), eventStickerDescription, place.eventDTO.getStatus(), new AsyncCallback<EventDTO>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails(constants.errorEventWasCanceled());
                    }

                    @Override
                    public void onSuccess(EventDTO result) {
                        notificationManager.showInfoPopup(constants.successEventWasCreated());
                        diaryListView.setNewEvent(result);
                        // // TODO: 26.12.2016 implement
                    }
                });
            }
        });
    }

    @Override
    public void start(HasWidgets display, NavigationPlace place) {
        if (place instanceof EditEventActivityPlace){
            this.place = (EditEventActivityPlace) place;
        }
        display.add((Widget) view);

        view.changeForm(this.place.eventDTO.getName(),this.place.eventDTO.getDescription(),this.place.eventDTO.getCategory(),
                this.place.eventDTO.getSticker(), this.place.eventDTO.getComplexity(), this.place.eventDTO.getStartTime(),
                this.place.eventDTO.getEndTime(),this.place.eventDTO.getDuration());
        eventStickerDescription = this.place.eventDTO.getSticker();
    }
    @Override
    public void stop() {}

    public static class EditEventActivityPlace extends NavigationPlace{

        private EventDTO eventDTO;

        public EditEventActivityPlace(EventDTO eventDTO)
        {
            super(NavigationUrl.URL_EDIT_EVENT_ACTIVITY);
            this.eventDTO = eventDTO;
        }
    }
}

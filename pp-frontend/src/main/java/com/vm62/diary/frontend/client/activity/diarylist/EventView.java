package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.vm62.diary.common.constants.Status;
import com.google.inject.Inject;
import com.vm62.diary.frontend.client.common.components.Signs;
import com.vm62.diary.frontend.client.common.dialogs.NotificationManager;
import com.vm62.diary.frontend.client.common.messages.DiaryConstants;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.service.EventServiceAsync;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;

import java.util.Date;

/**
 * Created by Maria.
 */
public class EventView extends Composite {
    private Label name = new Label();
    private Label time = new Label();
    private Label description = new Label();
    private FocusPanel wrapper = new FocusPanel();

    private Image sticker;
    private boolean stickerExists = false;

    private long id;
    private String idString = "event-";

    private long rightHeight = 0;

    private boolean descriptionHidden = false;
    private boolean headerHidden = false;
    private boolean needDescriptionHiding = false;
    private boolean needHeaderHiding = false;

    private MaterialButton editButton = new MaterialButton(ButtonType.FLAT, "", new MaterialIcon(IconType.EDIT));
    private MaterialButton deleteButton = new MaterialButton(ButtonType.FLAT, "", new MaterialIcon(IconType.DELETE));
    private MaterialButton doneButton = new MaterialButton(ButtonType.FLAT, "", new MaterialIcon(IconType.CHECK));
    private MaterialButton undoneButton = new MaterialButton(ButtonType.FLAT, "", new MaterialIcon(IconType.CANCEL));
    private EventServiceAsync eventServiceAsync;
    private NavigationManager navigationManager;
    private NotificationManager notificationManager;
    private EventDTO event;

    private DiaryConstants constants = GWT.create(DiaryConstants.class);

    private int HEIGHT_OF_ROW = 37;

    @Inject
    public EventView(NavigationManager navigationManager){
        this.navigationManager = navigationManager;
    }

    public EventView(EventDTO eventDTO, NavigationManager navigationManager, EventServiceAsync eventServiceAsync, NotificationManager notificationManager) {
        this.event = eventDTO;
        this.notificationManager = notificationManager;
        this.eventServiceAsync = eventServiceAsync;
        this.navigationManager = navigationManager;
        VerticalPanel panel = new VerticalPanel();

        wrapper.add(panel);

        if (!this.event.getSticker().isEmpty()) {
            sticker = new Image(Signs.WORK_SIGN.getSignByDescription(this.event.getSticker()).getImage());
            stickerExists = true;
        }


        panel.add(time);
        panel.add(name);
        panel.add(description);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(doneButton);
        panel.add(undoneButton);
        if (stickerExists) {
            panel.add(sticker);
        }

        Date startDate = event.getStartTime();
        Date endDate = event.getEndTime();
        long eventHeight = HEIGHT_OF_ROW * (endDate.getTime() - startDate.getTime()) / (60000 * 60);
        long eventTop = (long)((startDate.getHours() + startDate.getMinutes() / 60.0) * HEIGHT_OF_ROW + 12);

        if (eventHeight < HEIGHT_OF_ROW) {
            eventHeight = HEIGHT_OF_ROW;
        }
        rightHeight = eventHeight;

        id = event.getId();
        idString += id;

        wrapper.getElement().getStyle().setProperty("top", eventTop + "px");
        wrapper.getElement().getStyle().setProperty("backgroundColor", event.getCategory().getColor());
        wrapper.getElement().getStyle().setProperty("height", eventHeight + "px");
        wrapper.getElement().getStyle().setProperty("minHeight", eventHeight + "px");

        setText(event.getName(), event.getDescription(), formatDateRange(startDate, endDate));
        setStyleNames();
        setClickEvents();
        checkHeight();

        if (event.getStatus() != Status.undefined) {
            doneButton.addStyleName("disabled");
            undoneButton.addStyleName("disabled");
        }

        if (event.getStatus() != Status.active) {
            editButton.addStyleName("disabled");
        }

        initWidget(wrapper);
    }

    private void setClickEvents() {
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                e.preventDefault();
                e.stopPropagation();
                navigationManager.navigate(new EditEventViewActivity.EditEventActivityPlace(event,EventView.this));
            }
        });

        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                e.preventDefault();
                e.stopPropagation();
                eventServiceAsync.deleteEventById(event.getId(), new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        notificationManager.showErrorPopupWithoutDetails(constants.errorEventWasNotDeleted());
                    }

                    @Override
                    public void onSuccess(Boolean result) {
                        notificationManager.showInfoPopup(constants.successEventWasDeleted());
                        deleteEventFromList();
                    }
                });
            }
        });

        undoneButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                e.preventDefault();
                e.stopPropagation();
                eventServiceAsync.update(event.getId(), event.getName(), event.getDescription(), event.getCategory(), event.getStartTime(),
                        event.getEndTime(), event.getComplexity(), event.getDuration(), event.getSticker(), Status.undone, new AsyncCallback<EventDTO>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                notificationManager.showErrorPopupWithoutDetails(constants.errorEventIsNotAvailable());
                            }

                            @Override
                            public void onSuccess(EventDTO result) {
                                notificationManager.showInfoPopup(constants.successStatusWasEdited());
                            }
                        });
            }
        });

        doneButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                e.preventDefault();
                e.stopPropagation();
                eventServiceAsync.update(event.getId(), event.getName(), event.getDescription(), event.getCategory(), event.getStartTime(),
                        event.getEndTime(), event.getComplexity(), event.getDuration(), event.getSticker(), Status.done, new AsyncCallback<EventDTO>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                notificationManager.showErrorPopupWithoutDetails(constants.errorEventIsNotAvailable());
                            }

                            @Override
                            public void onSuccess(EventDTO result) {
                                notificationManager.showInfoPopup(constants.successStatusWasEdited());
                            }
                        });
            }
        });

        wrapper.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (needDescriptionHiding) {
                    if (descriptionHidden) {
                        slideToggle("." + idString + " .event__description");
                        descriptionHidden = false;
                    } else {
                        slideToggle("." + idString + " .event__description");
                        descriptionHidden = true;
                    }
                }
                if (needHeaderHiding) {
                    if (headerHidden) {
                        slideToggle("." + idString + " .event__name");
                        headerHidden = false;
                    } else {
                        slideToggle("." + idString + " .event__name");
                        headerHidden = true;
                    }
                }
            }
        });
    }

    public void deleteEventFromList(){
        this.getWidget().setVisible(false);
    }

    private void setStyleNames() {
        editButton.setStyleName("event__edit btn-flat");
        deleteButton.setStyleName("event__delete btn-flat");
        doneButton.setStyleName("event__done btn-flat");
        undoneButton.setStyleName("event__undone btn-flat");

        name.setStyleName("event__name");
        description.setStyleName("event__description");
        time.setStyleName("event__time");

        if (stickerExists) {
            sticker.addStyleName("event__sticker");
        }

        wrapper.setStyleName("event " + idString);
    }

    private void setText(String nameString, String descriptionString, String timeString) {
        name.setText(nameString);
        description.setText(descriptionString);
        time.setText(timeString);
    }

    protected native void slideToggle(String selector) /*-{
        $wnd.jQuery(selector).slideToggle();
    }-*/;

    private void checkHeight() {
        if (rightHeight < 87) {
            description.getElement().getStyle().setProperty("display", "none");
            descriptionHidden = true;
            needDescriptionHiding = true;
            wrapper.getElement().getStyle().setProperty("height", "auto");
        }
        if (rightHeight < 58) {
            name.getElement().getStyle().setProperty("display", "none");
            headerHidden = true;
            needHeaderHiding = true;
        }
    }

    public void activateDoneControls() {
        doneButton.removeStyleName("disabled");
        undoneButton.removeStyleName("disabled");
    }

    private String formatDateRange(Date startDate, Date endDate) {
        String startHours = startDate.getHours() > 9 ? String.valueOf(startDate.getHours()) : "0" + startDate.getHours();
        String startMinutes = startDate.getMinutes() > 9 ? String.valueOf(startDate.getMinutes()) : "0" + startDate.getMinutes();
        String endHours = endDate.getHours() > 9 ? String.valueOf(endDate.getHours()) : "0" + endDate.getHours();
        String endMinutes = endDate.getMinutes() > 9 ? String.valueOf(endDate.getMinutes()) : "0" + endDate.getMinutes();
        return startHours + ":" + startMinutes + " - " + endHours + ":" + endMinutes;
    }

    public long getId() {
        return id;
    }
}

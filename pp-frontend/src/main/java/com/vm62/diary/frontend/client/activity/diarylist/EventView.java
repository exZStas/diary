package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;

import java.util.Date;

/**
 * Created by Maria.
 */
public class EventView extends Composite implements ClickHandler {
    private Label name = new Label();
    private Label time = new Label();
    private Label description = new Label();
    private VerticalPanel panel = new VerticalPanel();
    private FocusPanel wrapper = new FocusPanel();

    private long id;
    private String idString = "event-";

    private long rightHeight = 0;

    private boolean descriptionHidden = false;
    private boolean headerHidden = false;
    private boolean needDescriptionHiding = false;
    private boolean needHeaderHiding = false;

    private MaterialButton editButton = new MaterialButton(ButtonType.FLAT, "", new MaterialIcon(IconType.EDIT));
    private MaterialButton deleteButton = new MaterialButton(ButtonType.FLAT, "", new MaterialIcon(IconType.DELETE));

    private int HEIGHT_OF_ROW = 37;

    public EventView(EventDTO event) {
        Date startDate = event.getStartTime();
        Date endDate = event.getEndTime();
        wrapper.add(panel);

        panel.add(time);
        panel.add(name);
        panel.add(description);

        id = event.getId();
        idString += id;

        name.setText(event.getName());
        description.setText(event.getDescription());
        time.setText(this.formatDateRange(startDate, endDate));

        long eventHeight = HEIGHT_OF_ROW * (endDate.getTime() - startDate.getTime()) / (60000 * 60);
        long eventTop = (startDate.getHours() + startDate.getMinutes() / 60) * HEIGHT_OF_ROW + 12;

        if (eventHeight < HEIGHT_OF_ROW) {
            eventHeight = HEIGHT_OF_ROW;
        }
        rightHeight = eventHeight;

        editButton.setStyleName("event__edit btn-flat");
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
                event.stopPropagation();
            }
        });
        panel.add(editButton);

        deleteButton.setStyleName("event__delete btn-flat");
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.preventDefault();
                event.stopPropagation();
            }
        });
        panel.add(deleteButton);

        name.setStyleName("event__name");
        description.setStyleName("event__description");
        time.setStyleName("event__time");

        initWidget(wrapper);

        setStyleName("event " + idString);
        getElement().getStyle().setProperty("top", eventTop + "px");
        getElement().getStyle().setProperty("backgroundColor", event.getCategory().getColor());
        getElement().getStyle().setProperty("height", eventHeight + "px");
        getElement().getStyle().setProperty("minHeight", eventHeight + "px");

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

        checkHeight();
    }

    public void onClick(ClickEvent event) {

    }

    protected native void slideToggle(String selector) /*-{
        $wnd.jQuery(selector).slideToggle();
    }-*/;

    private void checkHeight() {
        //let's hardcode it for now
        if (rightHeight < 87) {
            description.getElement().getStyle().setProperty("display", "none");
            descriptionHidden = true;
            needDescriptionHiding = true;
            getElement().getStyle().setProperty("height", "auto");
        }
        if (rightHeight < 58) {
            name.getElement().getStyle().setProperty("display", "none");
            headerHidden = true;
            needHeaderHiding = true;
        }
    }

    private String formatDateRange(Date startDate, Date endDate) {
        String startHours = startDate.getHours() > 9 ? String.valueOf(startDate.getHours()) : "0" + startDate.getHours();
        String startMinutes = startDate.getMinutes() > 9 ? String.valueOf(startDate.getMinutes()) : "0" + startDate.getMinutes();
        String endHours = endDate.getHours() > 9 ? String.valueOf(endDate.getHours()) : "0" + endDate.getHours();
        String endMinutes = endDate.getMinutes() > 9 ? String.valueOf(endDate.getMinutes()) : "0" + endDate.getMinutes();
        return startHours + ":" + startMinutes + " - " + endHours + ":" + endMinutes;
    }

}

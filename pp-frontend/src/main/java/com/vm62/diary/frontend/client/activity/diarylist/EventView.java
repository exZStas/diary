package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

/**
 * Created by Maria.
 */
public class EventView extends Composite implements ClickHandler {
    private Label name = new Label();
    private Label time = new Label();
    private Label description = new Label();

    private int HEIGHT_OF_ROW = 37;

    public EventView(String eventName, String eventDescription, String eventTime, String topPos, String color, Date startDate, Date endDate) {
        VerticalPanel panel = new VerticalPanel();
        panel.add(time);
        panel.add(name);
        panel.add(description);

        name.setText(eventName);
        description.setText(eventDescription);
        time.setText("");

        long eventHeight = HEIGHT_OF_ROW * (endDate.getTime() - startDate.getTime()) / (60000 * 60);
        long eventTop = (startDate.getHours() + startDate.getMinutes() / 60) * HEIGHT_OF_ROW + 22;

        name.setStyleName("event__name");
        description.setStyleName("event__description");
        time.setStyleName("event__time");

        initWidget(panel);

        setStyleName("event");
        getElement().getStyle().setProperty("top", eventTop + "px");
        getElement().getStyle().setProperty("backgroundColor", color);
        getElement().getStyle().setProperty("height", eventHeight + "px");
    }

    public void onClick(ClickEvent event) {
        Object sender = event.getSource();
    }

    private void checkHeight() {

    }

    private String formatDateRange(Date startDate, Date endDate) {
        String startHours = startDate.getHours() > 9 ? String.valueOf(startDate.getHours()) : "0" + startDate.getHours();
        String startMinutes = startDate.getMinutes() > 9 ? String.valueOf(startDate.getMinutes()) : "0" + startDate.getMinutes();
        return startHours + ":" + startMinutes + " - " + endDate.getHours() + ":" + endDate.getMinutes();
    }

}

package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.common.constants.Category;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import com.vm62.diary.frontend.server.service.dto.EventDTO;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.ui.*;
import com.google.gwt.user.client.ui.Label;


import java.util.Date;
import java.util.List;

//@Singleton
public class DiaryListView extends Composite implements DiaryListActivity.IDiaryListView {
    private static DiaryListUiBinder uiBinder = GWT.create(DiaryListUiBinder.class);

    interface DiaryListUiBinder extends UiBinder<HTMLPanel, DiaryListView> {

    }

    @UiField
    protected MaterialNavBar navBar;
    @UiField
    protected MaterialButton btnAddEvent;
    @UiField
    protected HTMLPanel diaryPanel;
    @UiField
    MaterialLink changeBtn;
    @UiField
    HTMLPanel scheduleList;
    @UiField
    MaterialLink logOutBtn;
 /*   @UiField
    protected MaterialModal modal;
    @UiField
    protected MaterialTextBox eventName;
    @UiField
    protected MaterialCheckBox simple, complex;
    @UiField
    protected MaterialTimePicker tp;
    @UiField
    protected MaterialButton btnCreate;
    @UiField
    protected MaterialRange duration;
    @UiField
    MaterialLabel lblRange;*/

   // private Label currentTime = new Label();
   // Date today = new Date();
    private NavigationManager navigationManager;


    @Inject
    public DiaryListView(NavigationManager navigationManager) {
        //currentTime.setText(today.toString());
        //navBar.add(currentTime);
        this.navigationManager = navigationManager;
        setWidget(uiBinder.createAndBindUi(this));
        scheduleList.getElement().getStyle().setProperty("height", "calc(100% - 150px)");

        EventDTO testEvent = new EventDTO(Long.valueOf(123), "AWESOME NAME", "Not so awesome description", Category.education, new Date(116, 11, 15, 7, 0), new Date(116, 11, 15, 10, 0), false, Long.valueOf(10800000), "");
        EventDTO testEvent2 = new EventDTO(Long.valueOf(234), "Tes Test", "AAA BBB CCC DDD EEE FFF GGG HHH", Category.homework, new Date(116, 11, 15, 12, 0), new Date(116, 11, 15, 15, 0), false, Long.valueOf(10800000), "");
        EventDTO testEvent3 = new EventDTO(Long.valueOf(456), "Little thing", "But very very important", Category.homework, new Date(116, 11, 15, 5, 0), new Date(116, 11, 15, 6, 0), false, Long.valueOf(3600000), "");
        EventView EV1 = new EventView(testEvent);
        scheduleList.add(EV1);
        EventView EV2 = new EventView(testEvent2);
        scheduleList.add(EV2);
        EventView EV3 = new EventView(testEvent3);
        scheduleList.add(EV3);

    }

    public void setScheduleList(List<EventDTO> events) {
        for (int i = 0; i < events.size(); i++) {
            EventView event = new EventView(events.get(i));
            scheduleList.add(event);
        }
    }

    @UiHandler("btnAddEvent")
    void onOpenCreateEventWindow(ClickEvent e) {
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_CREATE_EVENT_ACTIVITY));
    }

    @UiHandler("changeBtn")
    void onOpenChangeForm(ClickEvent e){
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_CHANGE_PROFILE_ACTIVITY));
    }

    @UiHandler("logOutBtn")
    void onClickLogOutBtn(ClickEvent e){
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_MAIN));
    }




}

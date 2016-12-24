package com.vm62.diary.frontend.client.activity.diarylist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.vm62.diary.frontend.client.common.navigation.NavigationManager;
import com.vm62.diary.frontend.client.common.navigation.NavigationPlace;
import com.vm62.diary.frontend.client.common.navigation.NavigationUrl;
import gwt.material.design.addins.client.timepicker.MaterialTimePicker;
import gwt.material.design.client.ui.*;
import com.google.gwt.user.client.ui.Label;


import java.util.Date;

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



    }
    @UiHandler("btnAddEvent")
    void onOpenCreateEventWindow(ClickEvent e) {
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_CREATE_EVENT_ACTIVITY));
    }

    @UiHandler("changeBtn")
    void onOpenChangeForm(ClickEvent e){
        navigationManager.navigate(new NavigationPlace(NavigationUrl.URL_CHANGE_PROFILE_ACTIVITY));
    }




}
